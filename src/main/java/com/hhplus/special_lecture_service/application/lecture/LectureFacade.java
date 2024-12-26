package com.hhplus.special_lecture_service.application.lecture;

import com.hhplus.special_lecture_service.application.dto.*;
import com.hhplus.special_lecture_service.common.exception.OverCapacityException;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.LectureService;
import com.hhplus.special_lecture_service.domain.registration.Registration;
import com.hhplus.special_lecture_service.domain.registration.RegistrationService;
import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.domain.user.UserService;
import com.hhplus.special_lecture_service.interfaces.api.dto.LectureRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final UserService userService;
    private final LectureService lectureService;
    private final RegistrationService registrationService;

    // 특강 신청하기
    public RegistrationResult lectureRegist(RegistrationParam registrationParam){

        long userId = registrationParam.getUserId();
        long lectureId = registrationParam.getLectureId();
        //1. user 도메인 : 사용자 id로 사용자 있는지 체크
        User user = userService.getUserById(userId);
        //2. lecture 도메인 : 특강 id로 특강 있는지 체크
        Lecture lecture = lectureService.getLectureById(lectureId);

        //3. registration 도메인 : 특강 신청 하기
        Registration registration = registrationService.lectureRegist(user, lecture);
        //4. registration 도메인 : 상태가 'COMPLETED'인 특강 신청 갯수 가져오기,
        int completedCount = registrationService.countCompletedRegistration(lecture.getId());

        //5. lecture 도메인 : 특강 신청 후 정보 업데이트
        lectureService.updateAfterLectureRegist(lecture.getId(), completedCount);

        return RegistrationResult.toServiceDto(registration);
    }


    public List<LectureResult> applicableLectures(LectureParam lectureParam) {
        //1. 날짜 validation 체크
        String date = lectureParam.getDate();
        lectureService.validDate(date);
        //2. 날짜가 입력받은 날짜이고 특강의 isAvailable이 'Y'인 것만 조회
        List<Lecture> applicableLectures =  lectureService.applicableLectures(date);
        return applicableLectures.stream()
                .map(LectureResult :: toServiceDto)
                .collect(Collectors.toList());
    }
}
