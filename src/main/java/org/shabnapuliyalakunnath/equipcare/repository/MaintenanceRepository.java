package org.shabnapuliyalakunnath.equipcare.repository;

import org.shabnapuliyalakunnath.equipcare.entity.Equipment;
import org.shabnapuliyalakunnath.equipcare.entity.Maintenance;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends CrudRepository<Maintenance,Long> {
    List<Maintenance> findByEquipmentAndStatus(Equipment equipment, String completed);

    List<Maintenance> findByStatusNot(String status);

    List<Maintenance> findByUser(User user);
    @Query(value = "SELECT status, count(status) FROM maintenance group by status", nativeQuery = true)
    List<Object[]> countByStatus();
    @Query(value = "SELECT count(*) FROM maintenance where status != 'Completed' and due_date < now()", nativeQuery = true)
    Long getOverDueCount();

    @Query(value = "SELECT status, count(status) FROM maintenance where user_id=:userId  group by status", nativeQuery = true)
    List<Object[]> countByStatusByUser(Long userId);
    @Query(value = "SELECT count(*) FROM maintenance where status != 'Completed' and user_id=:userId and due_date > now()", nativeQuery = true)
    Long getOverDueCountByUser(Long userId);
    List<Maintenance> findByUserAndStatusNot(User user, String status);
}
