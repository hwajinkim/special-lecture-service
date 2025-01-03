package com.hhplus.special_lecture_service.integrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hhplus.special_lecture_service.application.dto.RegistrationParam;
import com.hhplus.special_lecture_service.application.dto.RegistrationResult;
import com.hhplus.special_lecture_service.application.lecture.LectureFacade;
import com.hhplus.special_lecture_service.domain.lecture.*;
import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.integrationTest.setUp.LectureSetUp;
import com.hhplus.special_lecture_service.integrationTest.setUp.UserSetUp;
import com.hhplus.special_lecture_service.interfaces.api.dto.RegistrationRequest;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class LectureLimitThirtyIntegrationTest{

    @Autowired
    private UserSetUp userSetUp;

    @Autowired
    private LectureSetUp lectureSetUp;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private EntityManager entityManager;

    private Lecture lecture;
    private List<User> users = new ArrayList<>();
    @BeforeEach
    void setUp(){
        for (int i = 0; i < 40; i++) {
            users.add(userSetUp.saveUser("user" + i)); // 사용자 미리 생성
        }
        lecture = lectureSetUp.saveLecture("스프링 강좌", "홍길동", LocalDate.of(2024,12,25),
                LocalTime.of(10,00,00), LocalTime.of(12,00,00),10, 'Y');
    }

    @Test
    @DisplayName("동일한 특강에 대해 40명 신청 시 30명만 성공하도록 테스트")
    public void testConcurrentRegistrationLimit() throws InterruptedException {
        //given
        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        AtomicInteger successfulRequests = new AtomicInteger();
        AtomicInteger failedRequests = new AtomicInteger();

        //when
        for(int i = 0; i < threadCount; i++){
            User user = users.get(i);

            executorService.submit(() -> {
                try {
                    Registration registration = lectureService.lectureRegist(user.getId(), lecture.getId());
                    successfulRequests.incrementAndGet();
                } catch (Exception e) {
                    failedRequests.incrementAndGet();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        //then
        assertEquals(30, successfulRequests.get(), "성공한 요청은 30개여야 합니다.");
        assertEquals(10, failedRequests.get(), "실패한 요청은 10개여야 합니다.");
        assertEquals(30, registrationRepository.countCompletedRegistrationByLectureId(lecture.getId()), "데이터베이스에 저장된 신청은 30개여야 합니다.");
    }
}
