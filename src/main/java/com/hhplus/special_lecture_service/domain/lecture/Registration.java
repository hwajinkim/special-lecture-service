package com.hhplus.special_lecture_service.domain.lecture;

import com.hhplus.special_lecture_service.common.exception.OverCapacityException;
import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="registration_id", unique = true, nullable = false)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="lecture_id", nullable = false)
    private Long lectureId;

    @Column
    private String lectureName;

    @Column
    private String speaker;

    @Column
    private LocalDate lectureDate;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @CreationTimestamp
    private Timestamp appliedAt;

    @Builder
    public Registration(Long id, Long userId, Long lectureId, String lectureName, String speaker, LocalDate lectureDate, LocalTime startTime, LocalTime endTime, StatusType status){
        this.id = id;
        this.userId = userId;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.speaker = speaker;
        this.lectureDate = lectureDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public static Registration toSaveReturn(Long userId, Lecture lecture){
        return new Registration(null, userId, lecture.getId(), lecture.getLectureName(),
                lecture.getSpeaker(), lecture.getLectureDate(), lecture.getStartTime(), lecture.getEndTime(),
                StatusType.COMPLETED);
    }

    public static Registration regist(Long userId, Lecture lecture, int completedCount) {
        //3. registCount() ≥ 30, throws
        if(completedCount >= 30){
            throw new OverCapacityException("신청 가능 인원을 초과했습니다.");
        }

        Registration registration = Registration.toSaveReturn(userId, lecture);
        return registration;
    }

}
