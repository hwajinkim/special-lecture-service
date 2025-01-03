package com.hhplus.special_lecture_service.domain.lecture;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {

    Optional<Lecture> findByIdWithPessimisticLock(Long lectureId);

    void save(Lecture lecture);

    List<Lecture> findApplicableLectures(String date);

}
