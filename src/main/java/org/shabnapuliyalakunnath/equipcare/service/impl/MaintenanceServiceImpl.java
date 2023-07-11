//Implementation of maintenance service


package org.shabnapuliyalakunnath.equipcare.service.impl;
import org.shabnapuliyalakunnath.equipcare.dto.History;
import org.shabnapuliyalakunnath.equipcare.dto.MaintenanceDto;
import org.shabnapuliyalakunnath.equipcare.dto.MaintenanceHistoryDto;
import org.shabnapuliyalakunnath.equipcare.entity.Equipment;
import org.shabnapuliyalakunnath.equipcare.entity.Maintenance;
import org.shabnapuliyalakunnath.equipcare.entity.MaintenanceHistory;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.repository.EquipmentRepository;
import org.shabnapuliyalakunnath.equipcare.repository.MaintenanceHistoryRepository;
import org.shabnapuliyalakunnath.equipcare.repository.MaintenanceRepository;
import org.shabnapuliyalakunnath.equipcare.repository.UserRepository;
import org.shabnapuliyalakunnath.equipcare.service.MaintenanceService;
import org.shabnapuliyalakunnath.equipcare.dto.MaintCountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    MaintenanceRepository maintenanceRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MaintenanceHistoryRepository maintenanceHistoryRepository;
     // method to assign maintenance
    @Override
    public String maintainEquipment(MaintenanceDto maintain) {
        Maintenance maintenance = new Maintenance();
        maintenance.setEquipment(equipmentRepository.findById(maintain.getEquipmentId()).get());
        maintenance.setUser(userRepository.findById(maintain.getUser()).get());
        maintenance.setDetails(maintain.getDetails());
        maintenance.setDue_date(maintain.getDueDate());
        maintenance.setCreated_date(new Date());
        maintenance.setCreated_by("Admin");
        maintenance.setStatus("Assigned");
        maintenance = maintenanceRepository.save(maintenance);
        createHistory(maintenance);
        return "Success";
    }

    private void createHistory(Maintenance maintenance) {
        MaintenanceHistory history = new MaintenanceHistory();
        history.setCreated_by("Admin");
        history.setMaintenance(maintenance);
        history.setStatus("Assigned");
        history.setCreated_date(new Date());
        maintenanceHistoryRepository.save(history);
    }

    @Override
    public List<MaintenanceDto> getMaintenance(String userName) {
        List<Maintenance> maintenanceList = new ArrayList<>();
        if(null == userName) {
            maintenanceList =  maintenanceRepository.findByStatusNot("Completed");
        }else {
            User user = userRepository.findByEmail(userName);
            maintenanceList =  maintenanceRepository.findByUserAndStatusNot(user,"Completed");
        }
        List<MaintenanceDto> maintenanceDtos = new ArrayList<>();
        for (Maintenance maintenance:maintenanceList) { //TODO : Replace with POJO Mapping
            MaintenanceDto maintenanceDto = new MaintenanceDto();
            maintenanceDto.setEquipmentName(maintenance.getEquipment().getEquipmentName());
            maintenanceDto.setDepartment(maintenance.getEquipment().getDepartment());
            maintenanceDto.setDetails(maintenance.getDetails());
            maintenanceDto.setDueDate(maintenance.getDue_date());
            maintenanceDto.setUserName(maintenance.getUser().getFirstName() + " " + maintenance.getUser().getLastName());
            maintenanceDto.setStatus(maintenance.getStatus());
            maintenanceDto.setMaintenanceId(maintenance.getMaintenance_id());
            maintenanceDtos.add(maintenanceDto);
        }
        return maintenanceDtos;
    }

    @Override
    public History getMaintenanceHistory(Long equipId) {
        Equipment equipment = equipmentRepository.findById(equipId).get();
       List<Maintenance> maintenanceList =   maintenanceRepository.findByEquipmentAndStatus(equipment, "Completed");
        List<MaintenanceHistoryDto> maintenanceHistoryDtos = new ArrayList<>();
        for (Maintenance maintenance :maintenanceList) {
            MaintenanceHistoryDto maintenanceHistoryDto = new MaintenanceHistoryDto();
            maintenanceHistoryDto.setDate(maintenance.getCreated_date());
            maintenanceHistoryDto.setStatus(maintenance.getStatus());
            maintenanceHistoryDto.setDetails(maintenance.getDetails());
            maintenanceHistoryDto.setUserName(maintenance.getUser().getFirstName() + " "+ maintenance.getUser().getLastName());
            maintenanceHistoryDtos.add(maintenanceHistoryDto);
        }
        History history = new History();
        history.setEquipmentName(equipment.getEquipmentName());
        history.setMaintHistory(maintenanceHistoryDtos);
        return history;
    }

    @Override
    public MaintCountDto getMaintCount() {
        MaintCountDto maintCountDto = new MaintCountDto();
        List<Object[]> statusCount = maintenanceRepository.countByStatus();

        for (Object[] count:  statusCount) {
            String status = (String)count[0];
            Long counter =  count[1] == null ? 0L : (Long) count[1];
            if("Assigned".equalsIgnoreCase(status)) {
                maintCountDto.setPendingCount(counter);
            } else if ("inprogress".equalsIgnoreCase(status)) {
                maintCountDto.setInprogressCount(counter);
            }else if ("pending".equalsIgnoreCase(status)) {
                maintCountDto.setPendingCount(counter);
            }else if ("completed".equalsIgnoreCase(status)) {
                maintCountDto.setCompletedCount(counter);
            }
        }

        Long overDueCount = maintenanceRepository.getOverDueCount();
        maintCountDto.setOverDueCount(overDueCount);
        return maintCountDto;
    }

    @Override
    public MaintCountDto getMaintCount(String userName) {
        MaintCountDto maintCountDto = new MaintCountDto();
        User user = userRepository.findByEmail(userName);
        List<Object[]> statusCount = maintenanceRepository.countByStatusByUser(user.getUser_id());

        for (Object[] count:  statusCount) {
            String status = (String)count[0];
            Long counter =  count[1] == null ? 0L : (Long) count[1];
            if("Assigned".equalsIgnoreCase(status)) {
                maintCountDto.setPendingCount(counter);
            } else if ("inprogress".equalsIgnoreCase(status)) {
                maintCountDto.setInprogressCount(counter);
            }else if ("pending".equalsIgnoreCase(status)) {
                maintCountDto.setPendingCount(counter);
            }else if ("completed".equalsIgnoreCase(status)) {
                maintCountDto.setCompletedCount(counter);
            }
        }

        Long overDueCount = maintenanceRepository.getOverDueCountByUser(user.getUser_id());
        maintCountDto.setOverDueCount(overDueCount);
        return maintCountDto;
    }

    @Override
    public void changeStatus(Long maintId, String status) {
       Optional<Maintenance> maintenanceOp = maintenanceRepository.findById(maintId);
       if(maintenanceOp.isPresent()) {
           Maintenance maintenance = maintenanceOp.get();
           maintenance.setStatus(status);
           maintenanceRepository.save(maintenance);
       }
    }
}
