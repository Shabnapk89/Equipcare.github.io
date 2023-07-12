package org.shabnapuliyalakunnath.equipcare.repo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.shabnapuliyalakunnath.equipcare.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    UserRepository userRepository;

    @Test
    public void testFindUserByEmail() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        Optional<Object> result = userRepository.findUserByEmail(email);
        assertThat(result).isPresent().contains(user);
    }
}