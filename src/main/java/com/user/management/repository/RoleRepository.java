package com.user.management.repository;

import com.user.management.constant.RoleEnum;
import com.user.management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByRoleName(@Param(value = "roleName") RoleEnum roleName);

    boolean existsRoleByRoleName(RoleEnum roleName);

    @Modifying
    @Query(value = "update Role r set r.isDeleted = true where r.roleId = :roleId")
    void softDeleteRoleByRoleId(@Param(value = "roleId") UUID roleId);

    @Modifying
    @Query(value = "update Role r set r.isDeleted = true where r.roleName = :roleName")
    void softDeleteRoleByRoleName(@Param(value = "roleName") RoleEnum roleName);
}
