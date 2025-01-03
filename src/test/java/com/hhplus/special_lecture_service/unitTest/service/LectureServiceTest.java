package com.hhplus.special_lecture_service.unitTest.service;

import com.hhplus.special_lecture_service.common.exception.AlreadyExsitsRegistrationException;
import com.hhplus.special_lecture_service.common.exception.LectureNotFoundException;
import com.hhplus.special_lecture_service.common.exception.NotFoundApplicableLecturesException;
import com.hhplus.special_lecture_service.common.exception.NotFoundComletedRegistrationException;
import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.lecture.*;
import com.hhplus.special_lecture_service.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private User user;

    @Mock
    private Lecture mockLecture;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .id(1L)
                .username("김화진")
                .build();
        mockLecture = Lecture.builder()
                .id(1L)
                .lectureName("운영체제")
                .speaker("김철수")
                .lectureDate(LocalDate.of(2024, 12, 25))
                .startTime(LocalTime.of(10,00,00))
                .endTime(LocalTime.of(12,00,00))
                .applicantNumber(20)
                .build();
    }

    @Test
    @DisplayName("특강 신청하기 실패 테스트 - 이미 신청된 특강")
    void testLectureRegist_AlreadyExists(){
        //given
        Long lectureId = 1L;
        when(lectureRepository.findByIdWithPessimisticLock(lectureId)).thenReturn(Optional.of(mockLecture));
        when(registrationRepository.exsitsByUserIdAndLectureId(user.getId(), lectureId)).thenReturn(true);
        //when & then
        Exception exception = assertThrows(AlreadyExsitsRegistrationException.class,
                ()-> lectureService.lectureRegist(user.getId(), lectureId));

        assertEquals("이미 신청된 특강입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("특강 신청하기 테스트 - 저장 성공")
    void testLectureRegist_SavedSuccess(){
        //given
        Long lectureId = 1L;

        Registration mockRegistration = Registration.builder()
                .id(1L)
                .lectureName("운영체제")
                .speaker("김철수")
                .lectureDate(LocalDate.of(2024,12,25))
                .startTime(LocalTime.of(10,00,00))
                .endTime(LocalTime.of(12,00,00))
                .status(StatusType.COMPLETED)
                .build();

        when(lectureRepository.findByIdWithPessimisticLock(lectureId)).thenReturn(Optional.of(mockLecture));
        when(registrationRepository.exsitsByUserIdAndLectureId(user.getId(), lectureId)).thenReturn(false);
        when(registrationRepository.countCompletedRegistrationByLectureId(lectureId)).thenReturn(4);
        when(registrationRepository.save(any(Registration.class))).thenReturn(mockRegistration);

        //when
        Registration registration = lectureService.lectureRegist(user.getId(), lectureId);
        //then
        assertNotNull(registration);
        verify(registrationRepository).exsitsByUserIdAndLectureId(user.getId(), mockLecture.getId());
        verify(registrationRepository).countCompletedRegistrationByLectureId(mockLecture.getId());
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    @DisplayName("특강 신청 완료 목록 조회 시, 특강이 존재하지 않아 NotFoundComletedRegistrationException 발생")
    void testCompletedRegistration_NotFoundFail(){
        //given
        Long userId = 1L;
        when(registrationRepository.findCompletedRegistration(userId)).thenReturn(null);
        //when & then
        Exception exception = assertThrows(NotFoundComletedRegistrationException.class,
                ()-> lectureService.completedRegistration(userId));

        assertEquals("신청 완료된 특강 신청을 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("특강 신청 완료 목록 조회 시 성공")
    void testCompletedRegistration_Success(){
        //given
        Long userId = 1L;

        List<Registration> mockRegistrations = List.of(
                Registration.builder()
                        .id(1L)
                        .lectureName("스프링 강좌")
                        .speaker("강민수")
                        .lectureDate(LocalDate.of(2024,12,25))
                        .startTime(LocalTime.of(10,00,00))
                        .endTime(LocalTime.of(12,00,00))
                        .status(StatusType.COMPLETED)
                        .build(),
                Registration.builder()
                        .id(1L)
                        .lectureName("운영체제")
                        .speaker("김철수")
                        .lectureDate(LocalDate.of(2024,12,25))
                        .startTime(LocalTime.of(13,00,00))
                        .endTime(LocalTime.of(15,00,00))
                        .status(StatusType.COMPLETED)
                        .build()
        );
        when(registrationRepository.findCompletedRegistration(userId)).thenReturn(mockRegistrations);
        //when
        List<Registration> registrations = lectureService.completedRegistration(userId);

        //then
        assertNotNull(registrations);
        assertEquals(registrations, mockRegistrations);
    }

    @Test
    @DisplayName("특강 업데이트 실패 테스트 - 특강 찾을 수 없음.")
    void testUpdataeLecture_Fail(){
        //given
        long lectureId = 999L;
        int completedCount = 25;

        when(lectureRepository.findByIdWithPessimisticLock(lectureId)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(LectureNotFoundException.class,
                ()-> lectureService.updateAfterLectureRegist(lectureId, completedCount));

        assertEquals("특강을 찾을 수 없음.", exception.getMessage());
    }

    @Test
    @DisplayName("특강 업데이트 테스트 - 업데이트성공")
    void testUpdataeLecture_Success(){
        //given
        long lectureId = 1L;
        int completedCount = 25;

        when(lectureRepository.findByIdWithPessimisticLock(lectureId)).thenReturn(Optional.of(mockLecture));
        //when
        lectureService.updateAfterLectureRegist(lectureId, completedCount);

        //then
        verify(lectureRepository).findByIdWithPessimisticLock(lectureId);
        verify(lectureRepository).save(mockLecture);
    }

    @Test
    @DisplayName("특강 신청 가능 목록 조회 테스트 - 실패")
    void testApplicableLectures_fail(){
        //given
        String date = "2024-12-25";
        when(lectureRepository.findApplicableLectures(date)).thenReturn(null);

        //when
        assertThrows(NotFoundApplicableLecturesException.class,
                ()-> lectureService.applicableLectures(date));

        verify(lectureRepository).findApplicableLectures(date);
    }

    @Test
    @DisplayName("특강 신청 가능 목록 조회 테스트 - 성공")
    void testApplicableLectures_success(){
        //given
        String date = "2024-12-25";

        List<Lecture> mockLectures = List.of(
                Lecture.builder()
                        .id(1L)
                        .lectureName("스프링 강좌")
                        .speaker("강민수")
                        .lectureDate(LocalDate.of(2024,12,25))
                        .startTime(LocalTime.of(10,00,00))
                        .endTime(LocalTime.of(12,00,00))
                        .applicantNumber(20)
                        .isAvailable('Y')
                        .build(),
                Lecture.builder()
                        .id(2L)
                        .lectureName("운영체제")
                        .speaker("김철수")
                        .lectureDate(LocalDate.of(2024,12,25))
                        .startTime(LocalTime.of(13,00,00))
                        .endTime(LocalTime.of(15,00,00))
                        .applicantNumber(25)
                        .isAvailable('Y')
                        .build()
        );
        when(lectureRepository.findApplicableLectures(date)).thenReturn(mockLectures);

        //when
        List<Lecture> lectures = lectureService.applicableLectures(date);

        //then
        assertNotNull(lectures);
        assertEquals(lectures, mockLectures);
    }
}


