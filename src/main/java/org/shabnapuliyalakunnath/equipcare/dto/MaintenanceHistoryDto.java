package org.shabnapuliyalakunnath.equipcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MaintenanceHistoryDto {

    private Date date;
    private String status;

    private String details;

    private String userName;
}
