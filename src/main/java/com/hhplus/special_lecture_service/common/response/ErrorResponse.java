package com.hhplus.special_lecture_service.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private int code;
    private String message;
}
