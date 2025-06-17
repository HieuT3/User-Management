package com.user.management.entity;

import com.user.management.constant.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(length = 100)
    private String fullName;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 10)
    private String phone;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
