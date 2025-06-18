package com.user.management.controller;

import com.user.management.dto.request.LoginRequest;
import com.user.management.dto.request.RegistrationRequest;
import com.user.management.dto.response.ApiResponse;
import com.user.management.dto.response.RegistrationResponse;
import com.user.management.dto.response.UserResponse;
import com.user.management.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(
            @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest
    ) {
        log.info("Login request received for user: {}", loginRequest.getUsername());
        Authentication authentication = authService.login(loginRequest);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        httpServletRequest.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext
        );
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Login successful")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponse>> register(
            @Valid @RequestBody RegistrationRequest registrationRequest
            ) {
        log.info("Register request received for user: {}", registrationRequest.getUsername());
        RegistrationResponse registrationResponse = authService.register(registrationRequest);
        return ResponseEntity.ok(
                ApiResponse.<RegistrationResponse>builder()
                        .success(true)
                        .message("Registration successful")
                        .data(registrationResponse)
                        .build()
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile() {
        log.info("Profile request received");
        UserResponse userResponse = authService.getProfile();
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Profile retrieved successfully")
                        .data(userResponse)
                        .build()
        );
    }
}
