package com.hhplus.special_lecture_service.application.dto;

import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.lecture.Registration;
import com.hhplus.special_lecture_service.interfaces.api.dto.RegistrationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResult {

    private Long userId;
    private Long lectureId;
    private String lectureName;
    private String speaker;
    private LocalDate lectureDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private StatusType status;
    private Timestamp appliedAt;

    public static RegistrationResult toServiceDto(Registration registration) {
        return new RegistrationResult(
                registration.getUser().getId(),
                registration.getLecture().getId(),
                registration.getLectureName(),
                registration.getSpeaker(),
                registration.getLectureDate(),
                registration.getStartTime(),
                registration.getEndTime(),
                registration.getStatus(),
                registration.getAppliedAt()
        );
    }

    public static RegistrationResponse toControllerDto(RegistrationResult registrationResult) {
        return new RegistrationResponse(
                registrationResult.getUserId(),
                registrationResult.getLectureId(),
                registrationResult.getLectureName(),
                registrationResult.getSpeaker(),
                registrationResult.getLectureDate(),
                registrationResult.getStartTime(),
                registrationResult.getEndTime(),
                registrationResult.getStatus(),
                registrationResult.getAppliedAt()
        );
    }
}
