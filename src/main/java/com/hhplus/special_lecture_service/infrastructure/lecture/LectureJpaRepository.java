package com.hhplus.special_lecture_service.infrastructure.lecture;

import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.id = :lectureId")
    Optional<Lecture> findByIdWithPessimisticLock(@Param("lectureId") Long lectureId);

    @Query("SELECT l FROM Lecture l WHERE FUNCTION('DATE', l.lectureDate) = :date AND l.isAvailable = 'Y'")
    List<Lecture> findApplicableLectures(@Param("date") String date);
}
