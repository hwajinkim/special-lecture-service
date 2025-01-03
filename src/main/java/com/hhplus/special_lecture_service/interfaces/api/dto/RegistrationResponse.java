package com.hhplus.special_lecture_service.interfaces.api.dto;


import com.hhplus.special_lecture_service.application.dto.RegistrationResult;
import com.hhplus.special_lecture_service.domain.common.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
    private Long userId;
    private Long lectureId;
    private String lecture_name;
    private String speaker;
    private LocalDate lectureDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private StatusType status;
    private Timestamp appliedAt;

    public static RegistrationResponse toDto(RegistrationResult registrationResult){
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
