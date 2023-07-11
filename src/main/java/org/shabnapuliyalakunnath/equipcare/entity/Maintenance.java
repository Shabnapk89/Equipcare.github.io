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
@Table(name="maintenance")

public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long maintenance_id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;


    private String created_by;

    private Date created_date;

    private String details;

    private Date due_date;

    private String status;
}
