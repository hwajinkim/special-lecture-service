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
        String username = "kimhwajin";
        User user = userSetUp.saveUser(username);

        String lectureName = "스프링 강좌";
        String speaker = "홍길동";
        //특강 날짜
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 25); // 2024-12-25
        Date specificDate = calendar.getTime();
        Date date = specificDate;
        //특강 시작 시간
        Time sqlStartTime = Time.valueOf("10:00:00");
        Time startTime = sqlStartTime;
        //특강 종료 시간
        Time sqlEndTime = Time.valueOf("12:00:00");
        Time endTime = sqlEndTime;
        int applicantNumber = 10;
        char isAvailable = 'Y';

        Long lectureId = lectureSetUp.saveLecture(lectureName, speaker, date, startTime, endTime,applicantNumber, isAvailable);

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
        String lectureName = "스프링 강좌";
        String speaker = "홍길동";
        //특강 날짜
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 25); // 2024-12-25
        Date specificDate = calendar.getTime();
        Date date = specificDate;
        //특강 시작 시간
        Time sqlStartTime = Time.valueOf("10:00:00");
        Time startTime = sqlStartTime;
        //특강 종료 시간
        Time sqlEndTime = Time.valueOf("12:00:00");
        Time endTime = sqlEndTime;
        int applicantNumber = 10;
        char isAvailable = 'Y';

        Long lectureId = lectureSetUp.saveLecture(lectureName, speaker, date, startTime, endTime,applicantNumber, isAvailable);

        LectureRequest lectureRequest = new LectureRequest();
        lectureRequest.setDate(getDate);

        //when
        ResultActions resultActions = mvc.perform(get("/api/lectures/available?date="+lectureRequest.getDate())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", Matchers.is("true")))
                .andExpect(jsonPath("message", Matchers.is("신청 가능한 특강 조회에 성공했습니다.")))
                .andExpect(jsonPath("data", Matchers.is(notNullValue())));

    }
}
