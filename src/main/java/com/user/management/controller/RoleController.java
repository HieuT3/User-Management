package com.user.management.controller;

import com.user.management.constant.RoleEnum;
import com.user.management.dto.request.RoleRequest;
import com.user.management.dto.response.ApiResponse;
import com.user.management.dto.response.RoleResponse;
import com.user.management.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    RoleService roleService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        log.info("Fetching all roles");
        List<RoleResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(
                ApiResponse.<List<RoleResponse>>builder()
                        .success(true)
                        .data(roles)
                        .message("Roles fetched successfully")
                        .build()
        );
    }

    @GetMapping("{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable("roleId") UUID roleId) {
        log.info("Fetching role with ID: {}", roleId);
        RoleResponse role = roleService.getRoleByRoleId(roleId);
        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .data(role)
                        .message("Role fetched successfully")
                        .build()
        );
    }

    @GetMapping("name/{roleName}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleByName(@PathVariable("roleName") RoleEnum roleName) {
        log.info("Fetching role with name: {}", roleName);
        RoleResponse role = roleService.getRoleByRoleName(roleName);
        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .data(role)
                        .message("Role fetched successfully")
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<RoleResponse>> addRole(
            @Valid @RequestBody RoleRequest roleRequest
    ) {
        log.info("Creating new role: {}", roleRequest);
        RoleResponse createdRole = roleService.addRole(roleRequest);
        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .data(createdRole)
                        .message("Role created successfully")
                        .build()
        );
    }

    @DeleteMapping("{roleId}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable("roleId") UUID roleId) {
        log.info("Deleting role with ID: {}", roleId);
        roleService.softDeleteRoleByRoleId(roleId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Role deleted successfully")
                        .build()
        );
    }

    @DeleteMapping("name/{roleName}")
    public ResponseEntity<ApiResponse<Void>> deleteRoleByName(@PathVariable("roleName") RoleEnum roleName) {
        log.info("Deleting role with name: {}", roleName);
        roleService.softDeleteRoleByRoleName(roleName);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Role deleted successfully")
                        .build()
        );
    }
}
