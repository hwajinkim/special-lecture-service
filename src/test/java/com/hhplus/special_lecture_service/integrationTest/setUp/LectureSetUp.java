package com.hhplus.special_lecture_service.integrationTest.setUp;

import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.infrastructure.lecture.LectureJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;

@Component
public class LectureSetUp {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    public Long saveLecture(String lectureName, String speaker, Date date, Time startTime, Time endTime, int applicantNumber, char isAvailable){
           Lecture lecture = Lecture.builder()
                   .lectureName(lectureName)
                   .speaker(speaker)
                   .date(date)
                   .startTime(startTime)
                   .endTime(endTime)
                   .applicantNumber(applicantNumber)
                   .isAvailable(isAvailable)
                   .build();
           return lectureJpaRepository.save(lecture).getId();
    }
}
