package org.shabnapuliyalakunnath.equipcare.service;

import org.shabnapuliyalakunnath.equipcare.dto.History;
import org.shabnapuliyalakunnath.equipcare.dto.MaintCountDto;
import org.shabnapuliyalakunnath.equipcare.dto.MaintenanceDto;

import java.util.List;

public interface MaintenanceService {
    String maintainEquipment(MaintenanceDto maintain);

    List<MaintenanceDto> getMaintenance(String userName);

    History getMaintenanceHistory(Long maintId);

    MaintCountDto getMaintCount();

    MaintCountDto getMaintCount(String userName);

    void changeStatus(Long maintId, String status);
}
