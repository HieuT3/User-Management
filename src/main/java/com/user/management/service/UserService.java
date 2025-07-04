package com.user.management.service;

import com.user.management.dto.request.RegistrationRequest;
import com.user.management.dto.request.UpdateStatusUserRequest;
import com.user.management.dto.request.UpdateUserRequest;
import com.user.management.dto.request.UserRequest;
import com.user.management.dto.response.RegistrationResponse;
import com.user.management.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserByUserId(UUID userId);
    UserResponse getUserByUserName(String username);
    UserResponse addUser(UserRequest userRequest);
    RegistrationResponse registerUser(RegistrationRequest registrationRequest);
    UserResponse updateUserByUserId(UUID userId, UpdateUserRequest updateUserRequest);
    UserResponse updateStatusUserByUserId(UUID userId, UpdateStatusUserRequest updateStatusUserRequest);
    void deleteUserByUserId(UUID userId);

}
