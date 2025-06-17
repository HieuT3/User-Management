package com.user.management.repository;

import com.user.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);
}
