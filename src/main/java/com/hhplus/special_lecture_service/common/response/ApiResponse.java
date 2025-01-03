package com.hhplus.special_lecture_service.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hhplus.special_lecture_service.interfaces.api.dto.LectureResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApiResponse<T> {

    private int statusCode;
    private String success;
    private String message;
    private T data;

    private static final int SUCCESS = 200;

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<T>(SUCCESS, "true", message, data);
    }
}
