package com.user.management.dto.request;

import com.user.management.constant.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateStatusUserRequest {
    @NotNull
    private StatusEnum status;
}
