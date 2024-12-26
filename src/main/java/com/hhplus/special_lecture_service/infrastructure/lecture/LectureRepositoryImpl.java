package com.hhplus.special_lecture_service.infrastructure.lecture;

import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureJpaRepository.findById(id);
    }

    @Override
    public void save(Lecture lecture) {
        lectureJpaRepository.save(lecture);
    }
}
