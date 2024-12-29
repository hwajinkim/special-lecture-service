package com.hhplus.special_lecture_service.interfaces.api.lecture;

import com.hhplus.special_lecture_service.application.dto.*;
import com.hhplus.special_lecture_service.application.lecture.LectureFacade;
import com.hhplus.special_lecture_service.common.response.ApiResponse;
import com.hhplus.special_lecture_service.common.response.ResponseCode;
import com.hhplus.special_lecture_service.domain.registration.Registration;
import com.hhplus.special_lecture_service.interfaces.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    // 1. 특강 신청하기
    @PostMapping("/lecture/apply")
    public ApiResponse<RegistrationResponse> lectureApply(@RequestBody RegistrationRequest registrationRequest){

        RegistrationParam registrationParam = RegistrationParam.toServiceDto(registrationRequest);
        RegistrationResult registrationResult = lectureFacade.lectureRegist(registrationParam);
        RegistrationResponse registrationResponse = registrationResult.toControllerDto(registrationResult);

        return ApiResponse.success(ResponseCode.CREATE_SUCCESS.getMessage(), registrationResponse);
    }

    // 2. 신청 가능한 특강 날짜로 조회
    @GetMapping("/lectures/available")
    public ApiResponse<List<LectureResponse>> getAvailableLectures(@RequestParam(name="date") String date){

        List<LectureResult> lectureResults = lectureFacade.applicableLectures(date);

        List<LectureResponse> lectureResponses = new ArrayList<>();
        for(LectureResult lectureResult : lectureResults){
            LectureResponse lectureResponse = LectureResult.toControllerDto(lectureResult);
            lectureResponses.add(lectureResponse);
        }
        return ApiResponse.success(ResponseCode.READ_SUCCESS.getMessage(), lectureResponses);
    }

    // 3. 신청 완료 목록 조회
    @GetMapping("/lectures/completed")
    public ApiResponse<List<RegistrationResponse>> getCompletedRegistration(@RequestParam(name="userId") Long userId){

        List<RegistrationResult> registrationResults = lectureFacade.completedRegistration(userId);

        List<RegistrationResponse> registrationResponses = new ArrayList<>();
        for(RegistrationResult registrationResult : registrationResults){
            RegistrationResponse registrationResponse = RegistrationResult.toControllerDto(registrationResult);
            registrationResponses.add(registrationResponse);
        }
        return ApiResponse.success(ResponseCode.COMPLETED_READ_SUCCESS.getMessage(), registrationResponses);
    }

}
