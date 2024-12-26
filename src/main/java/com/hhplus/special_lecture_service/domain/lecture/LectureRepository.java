package com.hhplus.special_lecture_service.domain.lecture;

import java.util.Optional;

public interface LectureRepository {

    Optional<Lecture> findById(Long id);

    void save(Lecture lecture);
}
