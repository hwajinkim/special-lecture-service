package com.hhplus.special_lecture_service.domain.lecture;

import java.util.List;

public interface RegistrationRepository {
    boolean exsitsByUserIdAndLectureId(long userId, long lectuerId);

    int countCompletedRegistrationByLectureId(long lectuerId);

    Registration save(Registration registration);

    List<Registration> findCompletedRegistration(Long userId);
}
