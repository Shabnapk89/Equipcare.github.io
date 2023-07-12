package org.shabnapuliyalakunnath.equipcare.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.shabnapuliyalakunnath.equipcare.dto.History;
import org.shabnapuliyalakunnath.equipcare.dto.MaintCountDto;
import org.shabnapuliyalakunnath.equipcare.dto.MaintenanceDto;
import org.shabnapuliyalakunnath.equipcare.dto.UserDto;
import org.shabnapuliyalakunnath.equipcare.entity.Equipment;
import org.shabnapuliyalakunnath.equipcare.entity.Maintenance;
import org.shabnapuliyalakunnath.equipcare.entity.MaintenanceHistory;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.repository.EquipmentRepository;
import org.shabnapuliyalakunnath.equipcare.repository.MaintenanceHistoryRepository;
import org.shabnapuliyalakunnath.equipcare.repository.MaintenanceRepository;
import org.shabnapuliyalakunnath.equipcare.repository.UserRepository;
import org.shabnapuliyalakunnath.equipcare.service.impl.MaintenanceServiceImpl;
import org.shabnapuliyalakunnath.equipcare.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
public class MaintenanceServiceTest {


    @Mock
    private MaintenanceRepository maintenanceRepository;
    @Mock
    EquipmentRepository equipmentRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MaintenanceHistoryRepository maintenanceHistoryRepository;
    @InjectMocks // auto inject helloRepository
    private MaintenanceService maintenanceService = new MaintenanceServiceImpl();

    @DisplayName("Create Maintenance Test +ve")
    @Test
    void createMaintenanceTest() {
        when(equipmentRepository.findById(any())).thenReturn(Optional.of(new Equipment()));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));

        MaintenanceDto maintDto = new MaintenanceDto();
        maintDto.setDepartment("TestDept");
        maintDto.setDetails("Test Maintence");
        maintDto.setStatus("New");
        maintDto.setUserName("User");
        maintDto.setEquipmentId(4543L);
        maintDto.setDueDate(new Date());

        when(maintenanceRepository.save(any())).thenReturn(new Maintenance());
        when(maintenanceHistoryRepository.save(any())).thenReturn(new MaintenanceHistory());
        String res = maintenanceService.maintainEquipment(maintDto);
        assertEquals("Success", res);
    }

    };
