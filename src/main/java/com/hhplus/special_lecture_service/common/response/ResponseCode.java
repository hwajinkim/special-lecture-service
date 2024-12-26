package com.hhplus.special_lecture_service.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResponseCode {

    CREATE_SUCCESS(HttpStatus.CREATED, true, "특강 신청에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;
}
