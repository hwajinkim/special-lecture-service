package com.hhplus.special_lecture_service.integrationTest.setUp;

import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.Registration;
import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.infrastructure.registration.RegistrationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class RegistrationSetUp {

    @Autowired
    private RegistrationJpaRepository registrationJpaRepository;

    public Registration saveRegistration(User user, Lecture lecture, String lectureName, String speaker,
                                 LocalDate lectureDate, LocalTime startTime, LocalTime endTime, StatusType status){
        Registration registration = Registration.builder()
                        .user(user)
                        .lecture(lecture)
                        .lectureName(lectureName)
                        .speaker(speaker)
                        .lectureDate(lectureDate)
                        .startTime(startTime)
                        .endTime(endTime)
                        .status(status)
                        .build();

        return registrationJpaRepository.save(registration);
    }
}
