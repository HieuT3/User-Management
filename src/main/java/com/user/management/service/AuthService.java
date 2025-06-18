package com.user.management.service;

import com.user.management.dto.request.LoginRequest;
import com.user.management.dto.request.RegistrationRequest;
import com.user.management.dto.response.RegistrationResponse;
import com.user.management.dto.response.UserResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    Authentication login(LoginRequest loginRequest);
    RegistrationResponse register(RegistrationRequest registrationRequest);
    UserResponse getProfile();
}
