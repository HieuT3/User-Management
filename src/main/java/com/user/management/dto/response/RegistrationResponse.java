package com.user.management.dto.response;

import com.user.management.constant.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationResponse {
    private UUID userId;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String avatarUrl;
    private StatusEnum status;
    private boolean isDeleted;
    private Set<RoleResponse> roles;
}
