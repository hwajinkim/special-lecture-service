package com.hhplus.special_lecture_service.integrationTest.setUp;

import com.hhplus.special_lecture_service.domain.user.User;
import com.hhplus.special_lecture_service.domain.user.UserRepository;
import com.hhplus.special_lecture_service.infrastructure.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSetUp {

    @Autowired
    private UserJpaRepository userJpaRepository;

    public User saveUser(String username){
        User user = User.builder()
                .username(username)
                .build();
        return userJpaRepository.save(user);
    }
}
