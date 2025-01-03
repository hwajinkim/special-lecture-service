package com.hhplus.special_lecture_service.application.dto;

import com.hhplus.special_lecture_service.interfaces.api.dto.LectureRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureParam {

    private String date;
    public static LectureParam toServiceDto(LectureRequest lectureRequest) {
        return new LectureParam(
                lectureRequest.getDate()
        );
    }
}
