package com.user.management.service.implement;

import com.user.management.constant.RoleEnum;
import com.user.management.dto.request.RoleRequest;
import com.user.management.dto.response.RoleResponse;
import com.user.management.entity.Role;
import com.user.management.exception.AppException;
import com.user.management.exception.ErrorCode;
import com.user.management.mapper.RoleMapper;
import com.user.management.repository.RoleRepository;
import com.user.management.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::convertToRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse getRoleByRoleId(UUID roleId) {
        Role role = getRoleEntityByRoleId(roleId);
        return roleMapper.convertToRoleResponse(role);
    }

    @Override
    public RoleResponse getRoleByRoleName(RoleEnum roleName) {
        Role role = getRoleEntityByRoleName(roleName);
        return roleMapper.convertToRoleResponse(role);
    }

    @Override
    public RoleResponse addRole(RoleRequest roleRequest) {
        RoleEnum roleName = roleRequest.getRoleName();
        if (roleRepository.existsRoleByRoleName(roleName))
            throw new AppException(ErrorCode.ROLE_WITH_NAME_ALREADY_EXISTS);

        Role role = new Role();
        role.setRoleName(roleName);
        role.setDescription(roleRequest.getDescription());

        role = roleRepository.save(role);
        return roleMapper.convertToRoleResponse(role);
    }

    @Override
    @Transactional
    public void softDeleteRoleByRoleId(UUID roleId) {
        roleRepository.softDeleteRoleByRoleId(roleId);
    }

    @Override
    @Transactional
    public void softDeleteRoleByRoleName(RoleEnum roleName) {
        roleRepository.softDeleteRoleByRoleName(roleName);
    }

    private Role getRoleEntityByRoleId(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(
                        () -> new AppException(ErrorCode.ROLE_NOT_FOUND)
                );
    }

    @Override
    public Role getRoleEntityByRoleName(RoleEnum roleName) {
        return roleRepository.findRoleByRoleName(roleName)
                .orElseThrow(
                        () -> new AppException(ErrorCode.ROLE_NOT_FOUND)
                );
    }
}
