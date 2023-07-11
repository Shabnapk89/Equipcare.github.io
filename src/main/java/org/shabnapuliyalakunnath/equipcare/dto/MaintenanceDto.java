package org.shabnapuliyalakunnath.equipcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MaintenanceDto {

    private Long equipmentId;
    private String equipmentName;
    private String department;
    private String details;

    private Date dueDate;
    private Long user;
    private String userName;
    private String status;
    private Long maintenanceId;
}
