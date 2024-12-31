package com.hhplus.special_lecture_service.domain.registration;

import com.hhplus.special_lecture_service.common.exception.OverCapacityException;
import com.hhplus.special_lecture_service.domain.common.BaseEntity;
import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="registration_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="userId", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="lectureId", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Lecture lecture;

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
    public Registration(Long id, User user, Lecture lecture, String lectureName, String speaker, LocalDate lectureDate, LocalTime startTime, LocalTime endTime, StatusType status){
        this.id = id;
        this.user = user;
        this.lecture = lecture;
        this.lectureName = lectureName;
        this.speaker = speaker;
        this.lectureDate = lectureDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public static Registration toSaveReturn(User user, Lecture lecture){
        return new Registration(null, user, lecture, lecture.getLectureName(),
                lecture.getSpeaker(), lecture.getLectureDate(), lecture.getStartTime(), lecture.getEndTime(),
                StatusType.COMPLETED);
    }

    public static Registration regist(User user, Lecture lecture, int completedCount) {
        //3. registCount() ≥ 30, throws
        if(completedCount >= 30){
            throw new OverCapacityException("신청 가능 인원을 초과했습니다.");
        }

        Registration registration = Registration.toSaveReturn(user, lecture);
        return registration;
    }

}
