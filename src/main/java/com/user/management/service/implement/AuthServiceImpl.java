package com.user.management.service.implement;
import com.user.management.constant.StatusEnum;
import com.user.management.dto.request.LoginRequest;
import com.user.management.dto.request.RegistrationRequest;
import com.user.management.dto.response.RegistrationResponse;

import com.user.management.dto.response.UserResponse;
import com.user.management.entity.User;
import com.user.management.exception.AppException;
import com.user.management.exception.ErrorCode;

import com.user.management.mapper.UserMapper;
import com.user.management.security.CustomUserDetails;
import com.user.management.service.AuthService;
import com.user.management.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;
    UserService userService;
    UserMapper userMapper;

    @Override
    public Authentication login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", loginRequest.getUsername(), e);
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED);
        }

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = customUserDetails.getUser();
            if (user.isDeleted())
                throw new AppException(ErrorCode.ACCOUNT_HAS_BEEN_DELETED);

            if (user.getStatus().equals(StatusEnum.nonactive))
                throw new AppException(ErrorCode.ACCOUNT_NOT_ACTIVE);

            return authentication;
    }

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        return userService.registerUser(registrationRequest);
    }

    @Override
    public UserResponse getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        return userMapper.convertToUserResponse(user);
    }


}
