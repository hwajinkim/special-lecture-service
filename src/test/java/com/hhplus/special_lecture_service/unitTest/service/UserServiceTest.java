package com.hhplus.special_lecture_service.unitTest.service;

import com.hhplus.special_lecture_service.common.exception.UserNotFoundException;
import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.domain.user.UserRepository;
import com.hhplus.special_lecture_service.domain.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Test
    @DisplayName("사용자 id로 조회 실패 테스트 - 사용자 존재 안함")
    void testGetUserById_UserNotExists(){
        //given
        long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(()-> userService.getUserById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("사용자 찾을 수 없음.");
    }

    @Test
    @DisplayName("사용자 id로 조회 테스트 - 사용자 존재")
    void testGetUserById_UserExists(){
        //given
        long userId = 1L;
        String username = "hwajin";
        User mockUser = User.builder()
                .id(userId)
                .username(username)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        //when
        User user = userService.getUserById(userId);

        //then
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getUsername()).isEqualTo(username);
    }
}
