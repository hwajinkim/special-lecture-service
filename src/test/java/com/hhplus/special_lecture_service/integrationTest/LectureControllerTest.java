package com.hhplus.special_lecture_service.integrationTest;

import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.integrationTest.setUp.LectureSetUp;
import com.hhplus.special_lecture_service.integrationTest.setUp.UserSetUp;
import com.hhplus.special_lecture_service.interfaces.api.dto.LectureRequest;
import com.hhplus.special_lecture_service.interfaces.api.dto.RegistrationRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LectureControllerTest extends BaseIntegrationTest{

    @Autowired
    private UserSetUp userSetUp;

    @Autowired
    private LectureSetUp lectureSetUp;


    @Test
    @DisplayName("특강 신청 테스트")
    public void lectureApplyTest() throws Exception {
        //given
        User user = userSetUp.saveUser("kimhwajin");

        Long lectureId = lectureSetUp.saveLecture("스프링 강좌", "홍길동", LocalDate.of(2024,12,25),
                LocalTime.of(10,00,00), LocalTime.of(12,00,00),10, 'Y');

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUserId(user.getId());
        registrationRequest.setLectureId(lectureId);

        //when
        ResultActions resultActions = mvc.perform(post("/api/lecture/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", Matchers.is("true")))
                .andExpect(jsonPath("message", Matchers.is("특강 신청에 성공했습니다.")))
                .andExpect(jsonPath("data", Matchers.is(notNullValue())));

    }

    @Test
    @DisplayName("특강 신청 가능 목록 조회 테스트")
    public void getLecturesAvailableTest() throws Exception {
        //given
        String getDate = "2024-12-25";

        Long lectureId = lectureSetUp.saveLecture("스프링 강좌", "홍길동", LocalDate.of(2024,12,25),
                LocalTime.of(10,00,00), LocalTime.of(12,00,00),10, 'Y');

        LectureRequest lectureRequest = new LectureRequest();
        lectureRequest.setDate(getDate);

        //when
        ResultActions resultActions = mvc.perform(get("/api/lectures/available")
                        .param("date", getDate)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", Matchers.is("true")))
                .andExpect(jsonPath("message", Matchers.is("신청 가능한 특강 조회에 성공했습니다.")))
                .andExpect(jsonPath("data", Matchers.is(notNullValue())));
    }

    @Test
    void testGetAvailableLectures_MissingDate() throws Exception {
        mvc.perform(get("/api/lectures/available")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
