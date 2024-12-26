package com.hhplus.special_lecture_service.interfaces.api.dto;

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
public class LectureResponse {
    private Long lectureId;
    private String lectureName;
    private String speaker;
    private Date date;
    private Time startTime;
    private Time endTime;
}
