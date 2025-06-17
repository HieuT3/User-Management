package com.user.management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequest {

    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatarUrl;
}
