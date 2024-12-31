package com.hhplus.special_lecture_service.infrastructure.registration;

import com.hhplus.special_lecture_service.domain.lecture.Registration;
import com.hhplus.special_lecture_service.domain.lecture.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public List<Registration> findCompletedRegistration(Long userId) {
        return registrationJpaRepository.findCompletedRegistration(userId);
    }


}
