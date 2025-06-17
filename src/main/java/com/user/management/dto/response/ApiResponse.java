package com.user.management.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResponse <T> {
    private boolean success;
    private String message;
    private T data;
}
