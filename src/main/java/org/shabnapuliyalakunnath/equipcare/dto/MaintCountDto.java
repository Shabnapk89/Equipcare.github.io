package org.shabnapuliyalakunnath.equipcare.dto;

import lombok.Data;

@Data
public class MaintCountDto {

    private Long pendingCount=0l;
    private Long inprogressCount=0L;
    private Long completedCount=0L;
    private Long overDueCount=0L;
}
