package com.hhplus.special_lecture_service.application.dto;


import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.interfaces.api.dto.LectureResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureResult {

    private Long lectureId;
    private String lectureName;
    private String speaker;
    private Date date;
    private Time startTime;
    private Time endTime;

    public static LectureResult toServiceDto(Lecture lecture){
        return new LectureResult(
                lecture.getId(),
                lecture.getLectureName(),
                lecture.getSpeaker(),
                lecture.getDate(),
                lecture.getStartTime(),
                lecture.getEndTime()
        );
    }

    public static LectureResponse toContollerDto(LectureResult lectureResult) {
        return new LectureResponse(
                lectureResult.getLectureId(),
                lectureResult.getLectureName(),
                lectureResult.getSpeaker(),
                lectureResult.getDate(),
                lectureResult.getStartTime(),
                lectureResult.getEndTime()
        );
    }
}
