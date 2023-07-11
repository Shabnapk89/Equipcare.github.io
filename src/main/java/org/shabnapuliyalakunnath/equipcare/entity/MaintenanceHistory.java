package org.shabnapuliyalakunnath.equipcare.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="maintenance_history")

public class MaintenanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mh_id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "maintenance_id")
    private Maintenance maintenance;


    private String created_by;

    private Date created_date;

    private String details;

    private String status;

}
