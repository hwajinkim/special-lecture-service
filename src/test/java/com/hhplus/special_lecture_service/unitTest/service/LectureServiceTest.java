package com.hhplus.special_lecture_service.unitTest.service;

import com.hhplus.special_lecture_service.common.exception.LectureNotFoundException;
import com.hhplus.special_lecture_service.common.exception.NotFoundApplicableLecturesException;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.LectureRepository;
import com.hhplus.special_lecture_service.domain.lecture.LectureService;
import com.hhplus.special_lecture_service.domain.registration.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private Lecture mockLecture;

    @BeforeEach
    void setUp(){
        List<Registration> registrationList = new ArrayList<>();
        mockLecture = Lecture.builder()
                .id(1L)
                .lectureName("운영체제")
                .speaker("김철수")
                .lectureDate(LocalDate.of(2024, 12, 25))
                .startTime(LocalTime.of(10,00,00))
                .endTime(LocalTime.of(12,00,00))
                .applicantNumber(20)
                .registrationList(registrationList)
                .build();
    }

    @Test
    @DisplayName("특강 id로 조회 실패테스트 - 특강 존재 안함.")
    void testGetLectureById_LectureNotExist(){
        //given
        long lectureId = 999L;
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        //when & then
        assertThrows(LectureNotFoundException.class,
                ()-> lectureService.getLectureById(lectureId));
    }
    @Test
    @DisplayName("특강 id로 조회 테스트 - 특강 존재")
    void testGetLectureById_LectureExist(){
        //given
        long lectureId = 1;

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture));
        //when
        Lecture lecture = lectureService.getLectureById(lectureId);
        //then
        assertEquals(lecture, mockLecture);
    }

    @Test
    @DisplayName("특강 업데이트 실패 테스트 - 특강 찾을 수 없음.")
    void testUpdataeLecture_Fail(){
        //given
        long lectureId = 999L;
        int completedCount = 25;

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

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

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture));
        //when
        lectureService.updateAfterLectureRegist(lectureId, completedCount);

        //then
        verify(lectureRepository).findById(lectureId);
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


