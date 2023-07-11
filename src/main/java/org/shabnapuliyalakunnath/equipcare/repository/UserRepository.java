package org.shabnapuliyalakunnath.equipcare.repository;

import org.shabnapuliyalakunnath.equipcare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    User findByEmail(String email);
    void deleteByEmail(String l);

    List<User> findByRole(String staffIncharge);

    Optional<Object> findUserByEmail(String username);
}
