package com.user.management.dto.response;

import com.user.management.constant.StatusEnum;
import lombok.*;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID userId;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String avatarUrl;
    private StatusEnum status;
}
