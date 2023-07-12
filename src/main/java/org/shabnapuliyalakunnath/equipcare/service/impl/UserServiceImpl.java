
// Implementation class for  all user service classes
package org.shabnapuliyalakunnath.equipcare.service.impl;
import org.shabnapuliyalakunnath.equipcare.dto.UserDto;
import org.shabnapuliyalakunnath.equipcare.entity.Equipment;
import org.shabnapuliyalakunnath.equipcare.entity.Maintenance;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.exceptions.UserIdMismatchException;
import org.shabnapuliyalakunnath.equipcare.repository.EquipmentRepository;
import org.shabnapuliyalakunnath.equipcare.repository.MaintenanceRepository;
import org.shabnapuliyalakunnath.equipcare.repository.UserRepository;
import org.shabnapuliyalakunnath.equipcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    //Implementation of create user
    @Override
    public String createUser(UserDto user) {
        User userEntity = null;
        userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity != null) {
            throw new UserIdMismatchException("Email Id Already Used !!");   //Ex
        }
        userEntity = new User();
        userEntity.setCreated_by("Admin");  //TODO: Dynamic
        userEntity.setCreated_date(new Date());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setRole(user.getRole());
        try {
            userRepository.save(userEntity);
        } catch (Exception ex) {
            return "failed";
        }
        return "Success";
    }
   //Delete user Implementations
    @Override
    @Transactional
    public String deleteUser(String emailId) {
        User user = userRepository.findByEmail(emailId);
        List<Equipment> equipmentList = equipmentRepository.findByUser(user);
        List<Maintenance> maintenanceList=maintenanceRepository.findByUser(user);
        if(null != equipmentList && equipmentList.size() > 0) {
            return "User Cannot Be Deleted! User Associated with an equipment! UnAssign and Try again!!";
        } else if (null != maintenanceList && maintenanceList.size() > 0)  {
            return "User Cannot Be Deleted! User assigned maintenance  ! UnAssign and Try again!!";
        }
        else {
            userRepository.deleteByEmail(emailId);
            return "Success";
        }
    }
    // Method to get all users
    @Override
    public List<UserDto> getUsers() {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        List<User> users =  userRepository.findAll();
        for (User user:users) { //TODO : Replace with POJO Mapping
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            userDto.setPhone(user.getPhone());
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto getUser(String userId) {
        if(null == userId) return null;
        if("".equalsIgnoreCase(userId.trim())) return null;

        User user = userRepository.findByEmail(userId);
        UserDto userDto = null;
        if(null != user) {
            userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            userDto.setPhone(user.getPhone());
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());
            userDto.setUserId(user.getUser_id());
            userDto.setPassword(user.getPassword());
        }
        return userDto;
    }



    // Edit user implementation
    @Override
    public String edituser(UserDto user) {
        User userEntity = null;
        userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity != null && userEntity.getUser_id() != user.getUserId()) {
            return "Email Id Already Used !!";
        }
        Optional<User> userEt = null;
        if(userEntity == null) {
          userEt = userRepository.findById(user.getUserId());
          userEntity = userEt.get();
        }
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setRole(user.getRole());
        try {
            userRepository.save(userEntity);
        } catch (Exception ex) {

        }
        return "Success";
    }



    // checking user is eligible for registration
    @Override
    public Boolean isEligibleForRegister(String email) {
        UserDto user = getUser(email);
        if(null != user && ( user.getPassword() == null || "".equalsIgnoreCase(user.getPassword()))) {
            return true;
        }
        return false;
    }

    @Override
    public void register(UserDto userReq) {
        User user = userRepository.findByEmail(userReq.getEmail());
        //user.setPassword("{noop}"+userReq.getPassword());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        userRepository.save(user);
    }
}
