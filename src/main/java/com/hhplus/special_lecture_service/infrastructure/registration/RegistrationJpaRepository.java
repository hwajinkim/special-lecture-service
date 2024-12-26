package com.hhplus.special_lecture_service.infrastructure.registration;

import com.hhplus.special_lecture_service.domain.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserIdAndLectureId(long userId, long lectuerId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.lecture.id = :lectureId AND r.status = 'COMPLETED'")
    int countCompletedRegistrationByLectureId(@Param("lectureId") Long lectureId);
}
