package com.hhplus.special_lecture_service.application.dto;

import com.hhplus.special_lecture_service.interfaces.api.dto.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserParam {
    private String username;

    public static UserParam toServiceDto(UserRequest userRequest) {
        return new UserParam(
                userRequest.getUsername()
        );
    }
}
