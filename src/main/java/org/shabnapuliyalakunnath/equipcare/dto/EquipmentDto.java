package org.shabnapuliyalakunnath.equipcare.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EquipmentDto {
    private long equipmentId;

    @NotNull
    @NotEmpty(message = "Equipment name  Cannot Be Empty!!")
    private String equipmentName;
    @NotNull
    @NotEmpty(message = "Description  Cannot Be Empty!!")
    private String description ;
    @NotNull
    @NotEmpty(message = "Serial number  Cannot Be Empty!!")
    private String serialNo;
    @NotNull
    @NotEmpty(message = "Part number  Cannot Be Empty!!")
    private String  partNo;
    @NotNull
    @NotEmpty(message = "Status Cannot Be Empty!!")
    private String status;
    @NotNull
    @NotEmpty(message = "Department  Cannot Be Empty!!")
    private String department;
    @NotNull
    @NotEmpty(message = "User Cannot Be Empty!!")
    private String user;

}
