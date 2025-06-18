package com.user.management.service;

import com.user.management.constant.RoleEnum;
import com.user.management.dto.request.RoleRequest;
import com.user.management.dto.response.RoleResponse;
import com.user.management.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleResponse> getAllRoles();
    RoleResponse getRoleByRoleId(UUID roleId);
    RoleResponse getRoleByRoleName(RoleEnum roleName);
    RoleResponse addRole(RoleRequest roleRequest);
    void softDeleteRoleByRoleId(UUID roleId);
    void softDeleteRoleByRoleName(RoleEnum roleName);
    Role getRoleEntityByRoleName(RoleEnum roleName);
}
