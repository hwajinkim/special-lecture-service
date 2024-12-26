package com.hhplus.special_lecture_service.infrastructure.registration;

import com.hhplus.special_lecture_service.domain.registration.Registration;
import com.hhplus.special_lecture_service.domain.registration.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final RegistrationJpaRepository registrationJpaRepository;

    @Override
    public boolean exsitsByUserIdAndLectureId(long userId, long lectuerId) {
        return registrationJpaRepository.existsByUserIdAndLectureId(userId, lectuerId);
    }

    @Override
    public int countCompletedRegistrationByLectureId(long lectuerId) {
        return registrationJpaRepository.countCompletedRegistrationByLectureId(lectuerId);
    }

    @Override
    public Registration save(Registration registration) {
        return registrationJpaRepository.save(registration);
    }
}
