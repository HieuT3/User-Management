package com.user.management.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    String path;
    Map<String, String> errors;
}
