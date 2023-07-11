package org.shabnapuliyalakunnath.equipcare.service;

import org.shabnapuliyalakunnath.equipcare.dto.UserDto;

import java.util.List;

public interface UserService {

    String createUser(UserDto user);

    String deleteUser(String userId);

    List<UserDto> getUsers();

    UserDto getUser(String userId);

    String edituser(UserDto user);

    Boolean isEligibleForRegister(String email);

    void register(UserDto user);
}
