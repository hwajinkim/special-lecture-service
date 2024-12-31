package com.hhplus.special_lecture_service.unitTest.model;

import com.hhplus.special_lecture_service.common.exception.OverCapacityException;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.Registration;
import com.hhplus.special_lecture_service.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RegistrationTest {

    private User user;
    private Lecture lecture;

    @BeforeEach
    void setUp(){
        List<Registration> registrationList = new ArrayList<>();
        user = User.builder()
                .id(1L)
                .username("김화진")
                .build();

        lecture = Lecture.builder()
                .id(1L)
                .lectureName("운영체제")
                .speaker("김철수")
                .lectureDate(LocalDate.of(2024,12,25))
                .startTime(LocalTime.of(10,00,00))
                .endTime(LocalTime.of(12,00,00))
                .applicantNumber(20)
                .registrationList(registrationList)
                .build();
    }

    @Test
    @DisplayName("특강 등록 실패 테스트 - 정원(30명) 초과")
    void testRegist_OverCatacity() {
        //given
        int completedCount = 30;
        //when & then
        assertThatThrownBy(()-> Registration.regist(user, lecture, completedCount))
                .isInstanceOf(OverCapacityException.class)
                .hasMessage("신청 가능 인원을 초과했습니다.");
    }

    @Test
    @DisplayName("특강 등록 테스트")
    void testRegist(){
        //given
        Registration mockRegistration = Registration.toSaveReturn(user, lecture);
        int completedCount = 20;

        //when
        Registration registration = Registration.regist(user,lecture,completedCount);

        //then
        assertThat(registration).usingRecursiveComparison().isEqualTo(mockRegistration);
    }
}
