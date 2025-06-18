package com.user.management.service.implement;

import com.user.management.constant.RoleEnum;
import com.user.management.dto.request.RegistrationRequest;
import com.user.management.dto.request.UpdateStatusUserRequest;
import com.user.management.dto.request.UpdateUserRequest;
import com.user.management.dto.request.UserRequest;
import com.user.management.dto.response.RegistrationResponse;
import com.user.management.dto.response.UserResponse;
import com.user.management.entity.Role;
import com.user.management.entity.User;
import com.user.management.exception.AppException;
import com.user.management.exception.ErrorCode;
import com.user.management.mapper.UserMapper;
import com.user.management.repository.UserRepository;
import com.user.management.service.RoleService;
import com.user.management.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleService roleService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertToUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUserByUserId(UUID userId) {
        User user = getUserEntityByUserId(userId);
        return userMapper.convertToUserResponse(user);
    }

    @Override
    public UserResponse getUserByUserName(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_FOUND)
                );
        return userMapper.convertToUserResponse(user);
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        String username = userRequest.getUsername();
        if (userRepository.existsUserByUsername(username))
            throw new AppException(ErrorCode.USER_WITH_USERNAME_ALREADY_EXISTS);

        String email = userRequest.getEmail();
        if (userRepository.existsUserByEmail(email))
            throw new AppException(ErrorCode.USER_WITH_EMAIL_ALREADY_EXISTS);

        Set<RoleEnum> roles = userRequest.getRoles();

        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(email);
        user.setPhone(userRequest.getPhone());
        user.setAvatarUrl(userRequest.getAvatarUrl());

        if (roles != null && !roles.isEmpty()) {
            Set<Role> roleEntities = roles.stream()
                    .map(roleService::getRoleEntityByRoleName)
                    .collect(Collectors.toSet());
            user.setRoles(roleEntities);
        }
        user = userRepository.save(user);
        return userMapper.convertToUserResponse(user);
    }

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        if (userRepository.existsUserByUsername(username))
            throw new AppException(ErrorCode.USER_WITH_USERNAME_ALREADY_EXISTS);

        String email = registrationRequest.getEmail();
        if (userRepository.existsUserByEmail(email))
            throw new AppException(ErrorCode.USER_WITH_EMAIL_ALREADY_EXISTS);

        User user = new User();
        user.setFullName(registrationRequest.getFullName());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(email);
        user.setPhone(registrationRequest.getPhone());
        user.setAvatarUrl(registrationRequest.getAvatarUrl());

        Role userRole = roleService.getRoleEntityByRoleName(RoleEnum.user);
        user.setRoles(Set.of(userRole));

        user = userRepository.save(user);
        return userMapper.convertToRegistrationResponse(user);
    }

    @Override
    public UserResponse updateUserByUserId(UUID userId, UpdateUserRequest updateUserRequest) {
        User existingUserByUserId = getUserEntityByUserId(userId);

        User existingUserByEmail = userRepository.findUserByEmail(updateUserRequest.getEmail())
                .orElse(null);

        if (existingUserByEmail != null &&
            !existingUserByEmail.getUserId().equals(existingUserByUserId.getUserId())
        )
            throw new AppException(ErrorCode.USER_WITH_EMAIL_ALREADY_EXISTS);

        if (updateUserRequest.getFullName() != null)
            existingUserByUserId.setFullName(updateUserRequest.getFullName());
        if (updateUserRequest.getEmail() != null)
            existingUserByUserId.setEmail(updateUserRequest.getEmail());
        if (updateUserRequest.getPhone() != null)
            existingUserByUserId.setPhone(updateUserRequest.getPhone());
        if (updateUserRequest.getAvatarUrl() != null)
            existingUserByUserId.setAvatarUrl(updateUserRequest.getAvatarUrl());

        User updatedUser = userRepository.save(existingUserByUserId);
        return userMapper.convertToUserResponse(updatedUser);
    }

    @Override
    public UserResponse updateStatusUserByUserId(UUID userId, UpdateStatusUserRequest updateStatusUserRequest) {
        User user = getUserEntityByUserId(userId);
        user.setStatus(updateStatusUserRequest.getStatus());
        user = userRepository.save(user);
        return userMapper.convertToUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUserByUserId(UUID userId) {
        userRepository.softDeleteUserByUserId(userId);
    }

    private User getUserEntityByUserId(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_FOUND)
                );
    }
}
