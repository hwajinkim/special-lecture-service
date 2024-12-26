package com.hhplus.special_lecture_service.domain.user;

import com.hhplus.special_lecture_service.application.dto.UserParam;
import com.hhplus.special_lecture_service.common.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자 찾을 수 없음."));
        return user;
    }
}
