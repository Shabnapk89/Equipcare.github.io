
//Test cases for user service Implementations

package org.shabnapuliyalakunnath.equipcare.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.shabnapuliyalakunnath.equipcare.dto.UserDto;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.exceptions.UserIdMismatchException;
import org.shabnapuliyalakunnath.equipcare.repository.UserRepository;
import org.shabnapuliyalakunnath.equipcare.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks // auto inject helloRepository
    private UserService userService = new UserServiceImpl();


    @DisplayName("Create User Test +ve")
    @Test
    void createUserTest() {
        when(userRepository.findByEmail(any())).thenReturn(null);
        UserDto userDto = new UserDto();
        userDto.setFirstName("FN");
        userDto.setRole("Admin");
        userDto.setPhone("9199898989");
        userDto.setLastName("LN");
        userDto.setEmail("email.com");
        when(userRepository.save(any())).thenReturn(new User());
        String res = userService.createUser(userDto);
        assertEquals("Success", res);
    }
    @DisplayName("Create User Test Duplicate")
    @Test()
    void createUserTest_1() {
        when(userRepository.findByEmail(any())).thenReturn(new User());
        UserDto userDto = new UserDto();
        userDto.setFirstName("FN");
        userDto.setRole("Admin");
        userDto.setPhone("9199898989");
        userDto.setLastName("LN");
        userDto.setEmail("email.com");
        Throwable exception = assertThrows(UserIdMismatchException.class, () -> userService.createUser(userDto));
        assertEquals("Email Id Already Used !!", exception.getMessage());
    }

    // Parameterised test to user service
    @DisplayName("Get User Testing")
    @ParameterizedTest
    @ValueSource (strings = {"","  "})
    void getUsertest(String email) {
       assertNull(userService.getUser(email));
    }
    // Parameterized test to user service
    @DisplayName("Get User Testing")
    @ParameterizedTest
    @ValueSource (strings = {"admin@gmail.com","email44444@gmail.com"})
    void getUsertest_1(String email) {
        when(userRepository.findByEmail(any())).thenReturn(new User());
        assertNotNull(userService.getUser(email));
    }
}
