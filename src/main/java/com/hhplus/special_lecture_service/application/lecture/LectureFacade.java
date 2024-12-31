package com.hhplus.special_lecture_service.application.lecture;

import com.hhplus.special_lecture_service.application.dto.*;
import com.hhplus.special_lecture_service.common.exception.NotFoundApplicableLecturesException;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.LectureService;
import com.hhplus.special_lecture_service.domain.lecture.Registration;
import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final UserService userService;
    private final LectureService lectureService;
    // 1. 특강 신청하기
    public RegistrationResult lectureRegist(RegistrationParam registrationParam){

        long userId = registrationParam.getUserId();
        long lectureId = registrationParam.getLectureId();
        //1. user 도메인 : 사용자 id로 사용자 있는지 체크
        User user = userService.getUserById(userId);

        //3. registration 도메인 : 특강 신청 하기
        Registration registration = lectureService.lectureRegist(user, lectureId);
        //4. registration 도메인 : 상태가 'COMPLETED'인 특강 신청 갯수 가져오기,
        int completedCount = lectureService.countCompletedRegistration(lectureId);

        //5. lecture 도메인 : 특강 신청 후 정보 업데이트
        lectureService.updateAfterLectureRegist(lectureId, completedCount);

        return RegistrationResult.toServiceDto(registration);
    }

    // 2. 신청 가능한 특강 날짜로 조회
    public List<LectureResult> applicableLectures(String date) {
        //1. 날짜 validation 체크
        String returnDate = lectureService.validDate(date);
        //2. 날짜가 입력받은 날짜이고 특강의 isAvailable이 'Y'인 것만 조회
        List<Lecture> applicableLectures = lectureService.applicableLectures(returnDate);
        if(applicableLectures == null || applicableLectures.isEmpty()){
            throw new NotFoundApplicableLecturesException("신청 가능한 강의가 없습니다.");
        }
        return applicableLectures.stream()
                .map(LectureResult :: toServiceDto)
                .collect(Collectors.toList());
    }

    // 3. 신청 완료 목록 조회
    public List<RegistrationResult> completedRegistration(long userId) {
        //1. 사용자 ID 유효성 검증
        User user = userService.validUserId(userId);
        //2. registration 테이블에 사용자ID가 userId이고, status값이 'COMPLETED'인 데이터 조회
        List<Registration> registrations = lectureService.completedRegistration(userId);
        return registrations.stream()
                .map(RegistrationResult :: toServiceDto)
                .collect(Collectors.toList());
    }
}
