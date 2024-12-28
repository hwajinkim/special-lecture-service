package com.hhplus.special_lecture_service.unitTest.model;

import com.hhplus.special_lecture_service.common.exception.InvalidDateException;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class LectureTest {

    @Test
    @DisplayName("특강 id 유효성 실패 테스트 - null")
    void testValidLectureId_InputNull(){
        //given
        Long lectureId = null;
        //when & then
        assertThatThrownBy(()-> new Lecture(lectureId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("특강 ID 유효하지 않음.");
    }

    @Test
    @DisplayName("특강 id 유효성 실패 테스트 - 0")
    void testValidLectureId_InputZero(){
        //given
        long lectureId = 0;
        //when & then
        assertThatThrownBy(()-> new Lecture(lectureId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("특강 ID 유효하지 않음.");
    }

    @Test
    @DisplayName("특강 id 유효성 실패 테스트 - 음수")
    void testValidLectureId_InputNagative(){
        //given
        long lectureId = -1;
        //when & then
        assertThatThrownBy(()-> new Lecture(lectureId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("특강 ID 유효하지 않음.");
    }

    @Test
    @DisplayName("특강 id 유효성 테스트")
    void testValidLectureId(){
        //given
        long lectureId = 1L;
        //when
        Lecture lecture = new Lecture(lectureId);
        //then
        assertThat(lecture.getId()).isEqualTo(lectureId);
    }

    @Test
    @DisplayName("특강 날짜 유효성 테스트 - 실패")
    void testValidLectureDate_fail(){
        //given
        String date = "2024/12/25";
        //when & then
        assertThatThrownBy(()-> Lecture.validDate(date))
                .isInstanceOf(InvalidDateException.class)
                .hasMessage("날짜 형식이 유효하지 않음.");
    }

    @Test
    @DisplayName("특강 날짜 유효성 테스트 - Null실패")
    void testValidLectureDate_failNull(){
        //given
        String date = null;
        //when & then
        assertThatThrownBy(()-> Lecture.validDate(date))
                .isInstanceOf(InvalidDateException.class)
                .hasMessage("날짜 형식이 유효하지 않음.");
    }

    @Test
    @DisplayName("특강 날짜 유효성 테스트 - 성공")
    void testValidLectureDate_success(){
        //given
        String date = "2024-12-25";
        //when
        String getDate = Lecture.validDate(date);
        //then
        assertThat(getDate).isEqualTo(date);
    }
}
