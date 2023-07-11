// Implementations of Equipment crud operations

package org.shabnapuliyalakunnath.equipcare.service.impl;

import org.shabnapuliyalakunnath.equipcare.dto.EquipmentDto;
import org.shabnapuliyalakunnath.equipcare.dto.UserDto;
import org.shabnapuliyalakunnath.equipcare.entity.Equipment;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.repository.EquipmentRepository;
import org.shabnapuliyalakunnath.equipcare.repository.UserRepository;
import org.shabnapuliyalakunnath.equipcare.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EquipmentRepository equipmentRepository;

    @Override
    public List<UserDto> getStaffInchargeUsers() {
        List<User> users = userRepository.findByRole("StaffIncharge");
        List<UserDto> userDtos = new ArrayList<UserDto>();
        for (User user : users) { //TODO : Replace with POJO Mapping
            UserDto userDto = new UserDto();
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());
            userDto.setUserId(user.getUser_id());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public String createEquipment(EquipmentDto equipment) {


        Equipment equipmentEntity = null;
        equipmentEntity = equipmentRepository.findBySerialNo(equipment.getSerialNo());
        if (equipmentEntity != null) {
            return "Serial Number Already in Use !!";
        }
        equipmentEntity = new Equipment();
        equipmentEntity.setCreatedBy("Admin");  //TODO: Dynamic
        equipmentEntity.setCreatedDate(new Date());
        equipmentEntity.setSerialNo(equipment.getSerialNo());
        equipmentEntity.setEquipmentName(equipment.getEquipmentName());
        equipmentEntity.setPartNo(equipment.getPartNo());
        equipmentEntity.setDescription(equipment.getDescription());
        equipmentEntity.setDepartment(equipment.getDepartment());
        equipmentEntity.setStatus(equipment.getStatus());
        equipmentEntity.setUser(userRepository.findById(Long.parseLong(equipment.getUser())).get());
        try {
            equipmentRepository.save(equipmentEntity);
        } catch (Exception ex) {

        }
        return "Success";
    }

    @Override
    public List<EquipmentDto> getEquipments() {

        List<EquipmentDto> equipmentDtos = new ArrayList<EquipmentDto>();
        List<Equipment> equipments = (List<Equipment>) equipmentRepository.findAll();
        for (Equipment equipment:equipments) { //TODO : Replace with POJO Mapping
            EquipmentDto  equipmentDto = new EquipmentDto();
            equipmentDto.setEquipmentName(equipment.getEquipmentName());
            equipmentDto.setDescription(equipment.getDescription());
            equipmentDto.setDepartment(equipment.getDepartment());
            equipmentDto.setSerialNo(equipment.getSerialNo());
            equipmentDto.setStatus(equipment.getStatus());
            equipmentDto.setPartNo(equipment.getPartNo());
            equipmentDto.setUser(equipment.getUser().getFirstName() + " "+equipment.getUser().getLastName());
            equipmentDto.setEquipmentId(equipment.getEquipmentId());
            equipmentDtos.add(equipmentDto);
        }
        return equipmentDtos;
    }

    @Override
    public String deleteEquipment(String equipmentId) {
        equipmentRepository.deleteById(Long.parseLong(equipmentId));
        return "Success";
    }

    @Override
    public EquipmentDto getEquipment(String equipmentId) {
        Equipment equipment = equipmentRepository.findById(Long.parseLong(equipmentId)).get();
        EquipmentDto  equipmentDto = new EquipmentDto();
        equipmentDto.setEquipmentName(equipment.getEquipmentName());
        equipmentDto.setDescription(equipment.getDescription());
        equipmentDto.setDepartment(equipment.getDepartment());
        equipmentDto.setSerialNo(equipment.getSerialNo());
        equipmentDto.setStatus(equipment.getStatus());
        equipmentDto.setPartNo(equipment.getPartNo());
        equipmentDto.setUser(String.valueOf(equipment.getUser().getUser_id()));
        equipmentDto.setEquipmentId(equipment.getEquipmentId());
        return equipmentDto;
    }

    @Override
    public String edituser(EquipmentDto equipment) {

        Equipment equipmentObj = equipmentRepository.findBySerialNo(equipment.getSerialNo());
        if (equipmentObj != null && equipmentObj.getEquipmentId() != equipment.getEquipmentId()) {
            return "Serial Number Already in Use !!";
        }
        if(null == equipmentObj) {
            equipmentObj = equipmentRepository.findById(equipment.getEquipmentId()).get();
        }
        equipmentObj.setEquipmentName(equipment.getEquipmentName());
        equipmentObj.setDescription(equipment.getDescription());
        equipmentObj.setUser(userRepository.findById(Long.parseLong(equipment.getUser())).get());
        equipmentObj.setPartNo(equipment.getPartNo());
        equipmentObj.setSerialNo(equipment.getSerialNo());
        equipmentRepository.save(equipmentObj);
        return "Success";
    }

    @Override
    public List<UserDto> getEngineerUsers() {
        List<User> users = userRepository.findByRole("MaintenanceEngineer");
        List<UserDto> userDtos = new ArrayList<UserDto>();
        for (User user : users) { //TODO : Replace with POJO Mapping
            UserDto userDto = new UserDto();
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());
            userDto.setUserId(user.getUser_id());
            userDtos.add(userDto);
        }
        return userDtos;
    }


}
