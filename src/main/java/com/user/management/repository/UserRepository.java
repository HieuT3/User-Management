package com.user.management.repository;

import com.user.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    @Modifying
    @Query(value = "update User u set u.isDeleted = true, u.status = 'nonactive' where u.userId = :userId")
    void softDeleteUserByUserId(@Param(value = "userId") UUID userId);
}
