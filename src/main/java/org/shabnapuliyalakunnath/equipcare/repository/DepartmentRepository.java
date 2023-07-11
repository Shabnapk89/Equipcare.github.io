package org.shabnapuliyalakunnath.equipcare.repository;

import org.shabnapuliyalakunnath.equipcare.entity.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository <Department,Long>{
}
