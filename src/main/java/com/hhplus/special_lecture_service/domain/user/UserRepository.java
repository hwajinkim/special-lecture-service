package com.hhplus.special_lecture_service.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository{

    Optional<User> findById(Long id);
}
