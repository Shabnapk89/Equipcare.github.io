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
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String password;

    private String role;

    private String created_by;

    private Date created_date;

}
