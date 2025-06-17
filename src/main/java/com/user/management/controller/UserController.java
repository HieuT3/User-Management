package com.user.management.controller;

import com.user.management.dto.request.UpdateStatusUserRequest;
import com.user.management.dto.request.UpdateUserRequest;
import com.user.management.dto.request.UserRequest;
import com.user.management.dto.response.ApiResponse;
import com.user.management.dto.response.UserResponse;
import com.user.management.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        log.info("Fetching all users");
        List<UserResponse> userResponses = userService.getAllUsers();
        System.out.println(userResponses);
        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Users fetched successfully")
                        .data(userResponses)
                        .build()
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUserId(
            @PathVariable("userId") UUID userId
    ) {
        log.info("Fetching user with ID: {}", userId);
        UserResponse userResponse = userService.getUserByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User fetched successfully")
                        .data(userResponse)
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserResponse>> addUser(
            @Valid @RequestBody UserRequest userRequest
    ) {
        log.info("Adding new user: {}", userRequest.getUsername());
        UserResponse userResponse = userService.addUser(userRequest);
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User added successfully")
                        .data(userResponse)
                        .build()
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserByUserId(
            @PathVariable("userId") UUID userId,
            @RequestBody UpdateUserRequest updateUserRequest
            ) {
        log.info("Updating user with ID: {}", userId);
        UserResponse userResponse = userService.updateUserByUserId(userId, updateUserRequest);
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User updated successfully")
                        .data(userResponse)
                        .build()
        );
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateStatusUserByUserId(
            @PathVariable("userId") UUID userId,
            @Valid @RequestBody UpdateStatusUserRequest updateStatusUserRequest
            ) {
        log.info("Updating status for user with ID: {}", userId);
        UserResponse userResponse = userService.updateStatusUserByUserId(userId, updateStatusUserRequest);
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User status updated successfully")
                        .data(userResponse)
                        .build()
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> softDeleteUserByUserId(
            @PathVariable("userId") UUID userId
    ) {
        log.info("Deleting user with ID: {}", userId);
        userService.deleteUserByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("User deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
