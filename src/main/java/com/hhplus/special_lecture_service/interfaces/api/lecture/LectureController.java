package com.hhplus.special_lecture_service.interfaces.api.lecture;

import com.hhplus.special_lecture_service.application.dto.RegistrationParam;
import com.hhplus.special_lecture_service.application.dto.RegistrationResult;
import com.hhplus.special_lecture_service.application.dto.UserParam;
import com.hhplus.special_lecture_service.application.dto.UserResult;
import com.hhplus.special_lecture_service.application.lecture.LectureFacade;
import com.hhplus.special_lecture_service.common.response.ApiResponse;
import com.hhplus.special_lecture_service.common.response.ResponseCode;
import com.hhplus.special_lecture_service.interfaces.api.dto.RegistrationRequest;
import com.hhplus.special_lecture_service.interfaces.api.dto.RegistrationResponse;
import com.hhplus.special_lecture_service.interfaces.api.dto.UserRequest;
import com.hhplus.special_lecture_service.interfaces.api.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping("/lecture/apply")
    public ApiResponse<RegistrationResponse> lectureApply(@RequestBody RegistrationRequest registrationRequest){

        RegistrationParam registrationParam = RegistrationParam.toServiceDto(registrationRequest);
        RegistrationResult registrationResult = lectureFacade.lectureRegist(registrationParam);
        RegistrationResponse registrationResponse = registrationResult.toControllerDto(registrationResult);

        return ApiResponse.success(ResponseCode.CREATE_SUCCESS.getMessage(), registrationResponse);
    }

}
