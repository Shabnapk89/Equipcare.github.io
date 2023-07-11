package org.shabnapuliyalakunnath.equipcare.service;

import org.shabnapuliyalakunnath.equipcare.dto.EquipmentDto;
import org.shabnapuliyalakunnath.equipcare.dto.UserDto;

import java.util.List;

public interface EquipmentService {
    List<UserDto> getStaffInchargeUsers();

    String createEquipment(EquipmentDto equipment);

    List<EquipmentDto> getEquipments();

    String deleteEquipment(String equipmentId);

    EquipmentDto getEquipment(String equipmentId);

    String edituser(EquipmentDto equipment);

    List<UserDto> getEngineerUsers();
}
