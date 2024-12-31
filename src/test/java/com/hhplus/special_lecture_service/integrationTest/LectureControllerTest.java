package com.hhplus.special_lecture_service.integrationTest;

import com.hhplus.special_lecture_service.domain.common.StatusType;
import com.hhplus.special_lecture_service.domain.lecture.Lecture;
import com.hhplus.special_lecture_service.domain.lecture.Registration;
import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.integrationTest.setUp.LectureSetUp;
import com.hhplus.special_lecture_service.integrationTest.setUp.RegistrationSetUp;
import com.hhplus.special_lecture_service.integrationTest.setUp.UserSetUp;
import com.hhplus.special_lecture_service.interfaces.api.dto.LectureRequest;
import com.hhplus.special_lecture_service.interfaces.api.dto.RegistrationRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
public class LectureControllerTest extends BaseIntegrationTest{

    @Autowired
    private UserSetUp userSetUp;

    @Autowired
    private LectureSetUp lectureSetUp;

    @Autowired
    private RegistrationSetUp registrationSetUp;

    private User user;

    private Lecture lecture;
    @BeforeEach
    public void setUp() {
        // 필요한 테스트 데이터를 생성
        user = userSetUp.saveUser("kimhwajin");
        lecture = lectureSetUp.saveLecture("스프링 강좌", "홍길동", LocalDate.of(2024,12,25),
                LocalTime.of(10,00,00), LocalTime.of(12,00,00),10, 'Y');

    }
    @Test
    @DisplayName("특강 신청 테스트")
    public void lectureApplyTest() throws Exception {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUserId(user.getId());
        registrationRequest.setLectureId(lecture.getId());

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
    @DisplayName("특강 신청 완료 목록 조회 테스트")
    public void getCompletedRegistrationTest() throws Exception {
        //given
        Long userId = 1L;

        Registration registration = registrationSetUp.saveRegistration(user, lecture, "스프링 강좌", "홍길동",
                LocalDate.of(2024,12,25), LocalTime.of(10,00,00), LocalTime.of(12,00,00)
                , StatusType.COMPLETED);

        Registration registration2 = registrationSetUp.saveRegistration(user, lecture, "네트워크 개론", "김철수",
                LocalDate.of(2024,12,25), LocalTime.of(14,00,00), LocalTime.of(16,00,00)
                , StatusType.COMPLETED);

        //when
        ResultActions resultActions = mvc.perform(get("/api/lectures/completed")
                            .param("userId", String.valueOf(userId))
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", Matchers.is("true")))
                .andExpect(jsonPath("message", Matchers.is("특강 신청 완료된 목록 조회에 성공했습니다.")))
                .andExpect(jsonPath("data", Matchers.is(notNullValue())));
    }
}
