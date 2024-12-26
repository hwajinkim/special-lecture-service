package com.hhplus.special_lecture_service.infrastructure.user;

import com.hhplus.special_lecture_service.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}
