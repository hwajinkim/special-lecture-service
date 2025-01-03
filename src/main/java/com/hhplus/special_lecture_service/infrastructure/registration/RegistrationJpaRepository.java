package com.hhplus.special_lecture_service.infrastructure.registration;

import com.hhplus.special_lecture_service.domain.lecture.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserIdAndLectureId(long userId, long lectuerId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.lectureId = :lectureId AND r.status = 'COMPLETED'")
    int countCompletedRegistrationByLectureId(@Param("lectureId") Long lectureId);

    @Query("SELECT r FROM Registration r WHERE r.userId = :userId AND r.status = 'COMPLETED'")
    List<Registration> findCompletedRegistration(@Param("userId") Long userId);
}
