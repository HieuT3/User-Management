package com.user.management.bootstrap;

import com.user.management.constant.RoleEnum;
import com.user.management.entity.Role;
import com.user.management.entity.User;
import com.user.management.repository.RoleRepository;
import com.user.management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AdminAndRoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadRole();
        loadAdmin();
    }

    private void loadRole() {
        List<RoleEnum> roleTypes = List.of(RoleEnum.admin, RoleEnum.user);
        Map<RoleEnum, String> mp = Map.of(
                RoleEnum.admin, "This is the admin role, which has all permissions.",
                RoleEnum.user, "This is the user role, which has limited permissions."
        );
        roleTypes.forEach(
                roleEnum -> {
                    roleRepository.findRoleByRoleName(roleEnum).ifPresentOrElse(System.out::println, () -> {
                        Role role = new Role();
                        role.setRoleName(roleEnum);
                        role.setDescription(mp.get(roleEnum));
                        roleRepository.save(role);
                    });
                }
        );
    }

    private void loadAdmin() {
        if (userRepository.existsUserByUsername("admin")) return;
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        User user = new User();
        user.setFullName("ADMIN");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("Admin123456"));
        user.setEmail("admin@gmail.com");
        user.setPhone("0123456789");
        user.setAvatarUrl("URL");
        user.setRoles(roles);

        userRepository.save(user);
    }
}
