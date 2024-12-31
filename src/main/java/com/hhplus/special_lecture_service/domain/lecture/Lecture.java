package com.hhplus.special_lecture_service.domain.lecture;

import com.hhplus.special_lecture_service.common.exception.InvalidDateException;
import com.hhplus.special_lecture_service.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lectures")
public class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String lectureName;

    @Column(nullable = false)
    private String speaker;

    @Column(nullable = false)
    private LocalDate lectureDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column
    private int applicantNumber;

    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private char isAvailable;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.EAGER)
    private List<Registration> registrationList;

    public Lecture(Long id){
        if(!isValidId(id)){
            throw new IllegalArgumentException("특강 ID 유효하지 않음.");
        }
        this.id = id;
    }
   @Builder
    public Lecture(Long id, String lectureName, String speaker, LocalDate lectureDate, LocalTime startTime, LocalTime endTime, int applicantNumber, char isAvailable, List<Registration> registrationList){
        this.id = id;
        this.lectureName = lectureName;
        this.speaker = speaker;
        this.lectureDate = lectureDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.applicantNumber = applicantNumber;
        this.isAvailable = isAvailable;
        this.registrationList = registrationList;
    }

    public static boolean isValidId(Long id){
        return id != null &&  id > 0;
    }

    public static String validDate(String date) {
        if(date == null || date.isEmpty()){
            throw new InvalidDateException("날짜 형식이 유효하지 않음.");
        }
        try{
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch(Exception e){
            throw new InvalidDateException("날짜 형식이 유효하지 않음.");
        }
        return date;
    }

    public void update(Long lectureId, int completedCount) {
        this.applicantNumber = completedCount;
        if(completedCount >= 30){
            this.isAvailable = 'N';
        }
    }
}
