package com.user.management.mapper;

import com.user.management.dto.response.UserResponse;
import com.user.management.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse convertToUserResponse(User user);
}
