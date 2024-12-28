package com.hhplus.special_lecture_service.application.dto;


import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.interfaces.api.dto.LectureResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureResult {

    private Long lectureId;
    private String lectureName;
    private String speaker;
    private LocalDate lectureDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int applicantNumber;
    private char isAvailable;

    public static LectureResult toServiceDto(Lecture lecture){
        return new LectureResult(
                lecture.getId(),
                lecture.getLectureName(),
                lecture.getSpeaker(),
                lecture.getLectureDate(),
                lecture.getStartTime(),
                lecture.getEndTime(),
                lecture.getApplicantNumber(),
                lecture.getIsAvailable()
        );
    }

    public static LectureResponse toControllerDto(LectureResult lectureResult) {
        return new LectureResponse(
                lectureResult.getLectureId(),
                lectureResult.getLectureName(),
                lectureResult.getSpeaker(),
                lectureResult.getLectureDate(),
                lectureResult.getStartTime(),
                lectureResult.getEndTime(),
                lectureResult.getApplicantNumber(),
                lectureResult.getIsAvailable()
        );
    }
}
