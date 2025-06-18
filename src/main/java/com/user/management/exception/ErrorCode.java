package com.user.management.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum ErrorCode {

    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    USER_WITH_USERNAME_ALREADY_EXISTS("User with username already exists", HttpStatus.CONFLICT),
    USER_WITH_EMAIL_ALREADY_EXISTS( "User with email already exists", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND),
    ROLE_WITH_NAME_ALREADY_EXISTS("Role with name already exists", HttpStatus.CONFLICT),
    NOT_FOUND("Not found", HttpStatus.NOT_FOUND),
    AUTHENTICATION_FAILED("Authentication failed", HttpStatus.UNAUTHORIZED),
    ACCOUNT_HAS_BEEN_DELETED("Account has been deleted", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_ACTIVE("Account not active", HttpStatus.UNAUTHORIZED);

    String message;
    HttpStatusCode statusCode;
}
