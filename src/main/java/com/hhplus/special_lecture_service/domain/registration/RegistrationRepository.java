package com.hhplus.special_lecture_service.domain.registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository {
    boolean exsitsByUserIdAndLectureId(long userId, long lectuerId);

    int countCompletedRegistrationByLectureId(long lectuerId);

    Registration save(Registration registration);

    List<Registration> findCompletedRegistration(Long userId);
}
