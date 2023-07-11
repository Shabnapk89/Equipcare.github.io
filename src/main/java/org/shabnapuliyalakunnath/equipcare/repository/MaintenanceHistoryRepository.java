package org.shabnapuliyalakunnath.equipcare.repository;

import org.shabnapuliyalakunnath.equipcare.entity.Maintenance;
import org.shabnapuliyalakunnath.equipcare.entity.MaintenanceHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaintenanceHistoryRepository extends CrudRepository<MaintenanceHistory,Long> {
    List<MaintenanceHistory> findByMaintenance(Maintenance maintenance);
}
