package com.hhplus.special_lecture_service.infrastructure.lecture;

import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
