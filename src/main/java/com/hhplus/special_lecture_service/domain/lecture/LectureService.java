package com.hhplus.special_lecture_service.domain.lecture;

import com.hhplus.special_lecture_service.common.exception.LectureNotFoundException;
import com.hhplus.special_lecture_service.common.exception.OverCapacityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
