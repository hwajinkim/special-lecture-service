package com.hhplus.special_lecture_service.domain.registration;

import com.hhplus.special_lecture_service.common.exception.AlreadyExsitsRegistrationException;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    @Transactional
    public Registration lectureRegist(User user, Lecture lecture){
        //1. Registration 테이블에 userId, lectureId로 데이터 있는지 조회, 있으면 throws
        if(registrationRepository.exsitsByUserIdAndLectureId(user.getId(), lecture.getId())){
            throw new AlreadyExsitsRegistrationException("이미 신청된 특강입니다.");
        }
        //2. lectureId 전달, 조건은 state가 completed(신청완료)인 것 갯수 구하기
        int completedCount = registrationRepository.countCompletedRegistrationByLectureId(lecture.getId());

        //3. 특강 신청 호출
        Registration registration = Registration.regist(user, lecture, completedCount);
        //4. 특강 신청 저장
        Registration savedRegistration = registrationRepository.save(registration);

        return savedRegistration;
    }

    public int countCompletedRegistration(Long lectureId) {
        //registration.registCount() : lectureId 전달, 조건은 state가 'COMPLETED'(신청완료)인 것 갯수 구하기
        int completedCount = registrationRepository.countCompletedRegistrationByLectureId(lectureId);
        return completedCount;
    }
}
