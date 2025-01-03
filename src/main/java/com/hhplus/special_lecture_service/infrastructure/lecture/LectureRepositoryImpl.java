package com.hhplus.special_lecture_service.infrastructure.lecture;

import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<Lecture> findByIdWithPessimisticLock(Long lectureId) {
        return lectureJpaRepository.findByIdWithPessimisticLock(lectureId);
    }

    @Override
    public void save(Lecture lecture) {
        lectureJpaRepository.save(lecture);
    }

    @Override
    public List<Lecture> findApplicableLectures(String date) {
        return lectureJpaRepository.findApplicableLectures(date);
    }
}
