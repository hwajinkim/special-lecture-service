package com.hhplus.special_lecture_service.infrastructure.lecture;

import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    /*@Query("SELECT l FROM Lecture l WHERE l.lectureDate =:date AND l.isAvailable = 'Y'")*/
    @Query("SELECT l FROM Lecture l WHERE l.lectureDate = :date AND l.isAvailable = 'Y'")
    List<Lecture> findApplicableLectures(@Param("date") String date);
}
