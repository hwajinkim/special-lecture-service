package com.hhplus.special_lecture_service.domain.registration;

import com.hhplus.special_lecture_service.domain.common.BaseEntity;
import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.sql.Timestamp;
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

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="lectureId")
    private Lecture lecture;

    @Column
    private String lectureName;

    @Column
    private String speaker;

    @Column
    private Date date;

    @Column
    private Time startTime;

    @Column
    private Time endTime;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @CreationTimestamp
    private Timestamp appliedAt;

}
