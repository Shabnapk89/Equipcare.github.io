package org.shabnapuliyalakunnath.equipcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class History {

    private String equipmentName;
    private List<MaintenanceHistoryDto> maintHistory;
}
