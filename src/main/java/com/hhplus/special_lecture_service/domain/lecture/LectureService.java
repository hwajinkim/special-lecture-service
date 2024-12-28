package com.hhplus.special_lecture_service.domain.lecture;

import com.hhplus.special_lecture_service.common.exception.LectureNotFoundException;
import com.hhplus.special_lecture_service.common.exception.NotFoundApplicableLectures;
import com.hhplus.special_lecture_service.common.exception.OverCapacityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public Lecture  getLectureById(Long lectureId){
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()-> new LectureNotFoundException("특강을 찾을 수 없음."));
        return lecture;
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
            throw new NotFoundApplicableLectures("해당 날짜에 신청 가능한 강의가 없습니다.");
        }
        return lectures;
    }
}
