package org.shabnapuliyalakunnath.equipcare.repository;


import org.shabnapuliyalakunnath.equipcare.entity.Equipment;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment,Long>
{


    Equipment findBySerialNo(String serialNo);
    List<Equipment> findByUser(User user);
}
