package org.shabnapuliyalakunnath.equipcare.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.shabnapuliyalakunnath.equipcare.dto.EquipmentDto;
import org.shabnapuliyalakunnath.equipcare.dto.MaintenanceDto;
import org.shabnapuliyalakunnath.equipcare.entity.Equipment;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.repository.EquipmentRepository;
import org.shabnapuliyalakunnath.equipcare.repository.MaintenanceRepository;
import org.shabnapuliyalakunnath.equipcare.repository.UserRepository;
import org.shabnapuliyalakunnath.equipcare.service.impl.EquipmentServiceImpl;
import org.shabnapuliyalakunnath.equipcare.service.impl.MaintenanceServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EquipmentServiceTest {


    @Mock
    private EquipmentRepository equipmentRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks // auto inject helloRepository
    private EquipmentService equipmentService = new EquipmentServiceImpl();

    @DisplayName("Create Equipment Test +ve")
    @Test
    void createEquipmentTest() {
        when(equipmentRepository.findBySerialNo(any())).thenReturn(null);
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        EquipmentDto equiptDto = new EquipmentDto();
        equiptDto.setDepartment("TestDept");
        equiptDto.setEquipmentName("Test Equip");
        equiptDto.setDescription("Desc");
        equiptDto.setPartNo("123456");
        equiptDto.setSerialNo("123456");
        equiptDto.setDepartment("test Dept");
        equiptDto.setUser("1234");

        when(equipmentRepository.save(any())).thenReturn(new Equipment());
        String res = equipmentService.createEquipment(equiptDto);
        assertEquals("Success", res);
    }
}
