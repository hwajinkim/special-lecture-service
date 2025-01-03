package com.hhplus.special_lecture_service.application.dto;

import com.hhplus.special_lecture_service.interfaces.api.dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationParam {
    private Long userId;
    private Long lectureId;

    public static RegistrationParam toServiceDto(RegistrationRequest registrationRequest) {
        return new RegistrationParam(
                registrationRequest.getUserId(),
                registrationRequest.getLectureId()
        );
    }
}
