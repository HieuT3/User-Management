package com.user.management.dto.request;

import com.user.management.constant.RoleEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoleRequest {
    @NotNull(message = "Role name is required")
    private RoleEnum roleName;
    private String description;
}
