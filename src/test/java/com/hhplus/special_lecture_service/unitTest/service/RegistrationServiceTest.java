package com.hhplus.special_lecture_service.unitTest.service;

import com.hhplus.special_lecture_service.common.exception.AlreadyExsitsRegistrationException;
import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.registration.Registration;
import com.hhplus.special_lecture_service.domain.registration.RegistrationRepository;
import com.hhplus.special_lecture_service.domain.registration.RegistrationService;
import com.hhplus.special_lecture_service.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private User user;
    @Mock
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
                .date(Date.valueOf("2024-12-25"))
                .startTime(Time.valueOf("10:00:00"))
                .endTime(Time.valueOf("12:00:00"))
                .applicantNumber(20)
                .registrationList(registrationList)
                .build();
    }
    @Test
    @DisplayName("특강 신청하기 실패 테스트 - 이미 신청된 특강")
    void testLectureRegist_AlreadyExists(){
        //given

        when(registrationRepository.exsitsByUserIdAndLectureId(user.getId(), lecture.getId())).thenReturn(true);
        //when & then
        Exception exception = assertThrows(AlreadyExsitsRegistrationException.class,
                ()-> registrationService.lectureRegist(user, lecture));

        assertEquals("이미 신청된 특강입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("특강 신청하기 테스트 - 저장 성공")
    void testLectureRegist_SavedSuccess(){
        //given
        Registration mockRegistration = Registration.builder()
                .id(1L)
                .lectureName("운영체제")
                .speaker("김철수")
                .date(Date.valueOf("2024-12-25"))
                .startTime(Time.valueOf("10:00:00"))
                .endTime(Time.valueOf("12:00:00"))
                .status(StatusType.COMPLETED)
                .build();

        when(registrationRepository.exsitsByUserIdAndLectureId(user.getId(), lecture.getId())).thenReturn(false);
        when(registrationRepository.countCompletedRegistrationByLectureId(lecture.getId())).thenReturn(5);
        when(registrationRepository.save(any(Registration.class))).thenReturn(mockRegistration);

        //when
        Registration registration = registrationService.lectureRegist(user, lecture);
        //then
        assertNotNull(registration);
        verify(registrationRepository).exsitsByUserIdAndLectureId(user.getId(), lecture.getId());
        verify(registrationRepository).countCompletedRegistrationByLectureId(lecture.getId());
        verify(registrationRepository).save(any(Registration.class));
    }
}
