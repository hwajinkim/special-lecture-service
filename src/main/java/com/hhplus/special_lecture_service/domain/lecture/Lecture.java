package com.hhplus.special_lecture_service.domain.lecture;

import com.hhplus.special_lecture_service.domain.common.BaseEntity;
import com.hhplus.special_lecture_service.domain.registration.Registration;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

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
    private Date date;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;

    @Column
    private int applicantNumber;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.EAGER)
    private List<Registration> registrationList;
}
