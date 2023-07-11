package org.shabnapuliyalakunnath.equipcare.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    @NotNull
    @NotEmpty(message = "First Name Cannot Be Empty!!")
    private String firstName;
    @NotNull
    @NotEmpty(message = "last Name Cannot Be Empty!!")
    private String lastName;
    @NotNull
    @NotEmpty(message = "Email Id  Cannot Be Empty!!")
    private String email;
    @NotNull
    @NotEmpty(message = "Phone Number Cannot Be Empty!!")
    private String phone;
    @NotNull
    @NotEmpty(message = " Role Cannot Be Empty!!")
    private String role;
    private long userId;
    private String password;
}
