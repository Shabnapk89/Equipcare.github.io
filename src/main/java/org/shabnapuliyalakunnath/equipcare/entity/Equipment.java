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
@Table(name="equipment")

public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long equipmentId;

    private String serialNo;

    private String partNo;

    private String equipmentName;

    private String description;

    private String createdBy;

    private Date createdDate;

    private String status;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depId")
    private Department department;*/
    private String department;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;
}
