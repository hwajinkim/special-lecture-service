package com.hhplus.special_lecture_service.interfaces.api.lecture;

import com.hhplus.special_lecture_service.application.dto.*;
import com.hhplus.special_lecture_service.application.lecture.LectureFacade;
import com.hhplus.special_lecture_service.common.response.ApiResponse;
import com.hhplus.special_lecture_service.common.response.ResponseCode;
import com.hhplus.special_lecture_service.interfaces.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/lectures/available")
    public ApiResponse<List<LectureResponse>> getAvailableLectures(@RequestParam LectureRequest lectureRequest){

        LectureParam lectureParam = LectureParam.toServiceDto(lectureRequest);
        List<LectureResult> lectureResults = lectureFacade.applicableLectures(lectureParam);
        List<LectureResponse> lectureResponses = new ArrayList<>();
        for(LectureResult lectureResult : lectureResults){
            LectureResponse lectureResponse = LectureResult.toContollerDto(lectureResult);
            lectureResponses.add(lectureResponse);
        }
        return ApiResponse.success(ResponseCode.READ_SUCCESS.getMessage(), lectureResponses);
    }

}
