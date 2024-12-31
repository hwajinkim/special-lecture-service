package com.hhplus.special_lecture_service.domain.lecture;

import com.hhplus.special_lecture_service.common.exception.*;
import com.hhplus.special_lecture_service.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final RegistrationRepository registrationRepository;


    @Transactional
    public Registration lectureRegist(User user, Long lectureId){
        //0. 특강 id로 특강 있는지 체크
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()-> new LectureNotFoundException("특강을 찾을 수 없음."));

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

    public List<Registration> completedRegistration(Long userId) {
        //registration 테이블에 사용자ID가 userId이고, status값이 'COMPLETED'인 데이터 조회
        List<Registration> registrations = registrationRepository.findCompletedRegistration(userId);
        if(registrations == null || registrations.isEmpty()){
            throw new NotFoundComletedRegistrationException("신청 완료된 특강 신청을 찾을 수 없습니다.");
        }
        return registrations;
    }

    @Transactional
    public void updateAfterLectureRegist(Long lectureId, int completedCount) {

        Lecture lecture =  lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFoundException("특강을 찾을 수 없음."));

        // 특강 정보 업데이트
        lecture.update(lectureId, completedCount);

        lectureRepository.save(lecture);
    }

    public String validDate(String date) {
        return Lecture.validDate(date);
    }

    public List<Lecture> applicableLectures(String date) {
        List<Lecture> lectures =  lectureRepository.findApplicableLectures(date);
        if(lectures == null || lectures.isEmpty()){
            throw new NotFoundApplicableLecturesException("해당 날짜에 신청 가능한 강의가 없습니다.");
        }
        return lectures;
    }
}
