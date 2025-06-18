package com.user.management.dto.response;

import com.user.management.constant.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class RoleResponse {
    private UUID roleId;
    private RoleEnum roleName;
    private String description;
}
