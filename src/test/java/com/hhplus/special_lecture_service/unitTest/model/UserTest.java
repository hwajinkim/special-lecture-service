package com.hhplus.special_lecture_service.unitTest.model;

import com.hhplus.special_lecture_service.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


public class UserTest {
    @Test
    @DisplayName("사용자id 유효성 실패 테스트 - null")
    void testValidUserId_InputNull(){
        //given
        Long userId = null;
        //when & then
        assertThatThrownBy(()-> new User(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 ID 유효하지 않음.");
    }

    @Test
    @DisplayName("사용자id 유효성 실패 테스트 - 0")
    void testValidUserId_InputZero(){
        //given
        long userId = 0;
        //when & then
        assertThatThrownBy(()-> new User(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 ID 유효하지 않음.");
    }

    @Test
    @DisplayName("사용자id 유효성 실패 테스트 - 음수")
    void testValidUserId_InputNagative(){
        //given
        long userId = -1L;
        //when & then
        assertThatThrownBy(()-> new User(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 ID 유효하지 않음.");
    }

    @Test
    @DisplayName("사용자id 유효성 테스트")
    void testValidUserId(){
        // given
        long userId = 1L;
        //when
        User user = new User(userId);
        //then
        assertThat(user.getId()).isEqualTo(userId);
    }
}
