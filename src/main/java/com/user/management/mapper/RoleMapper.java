package com.user.management.mapper;

import com.user.management.dto.response.RoleResponse;
import com.user.management.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse convertToRoleResponse(Role role);
}
