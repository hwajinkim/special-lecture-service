package com.hhplus.special_lecture_service.unitTest.model;

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
}
