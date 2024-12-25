package com.hhplus.special_lecture_service.domain.user;

import com.hhplus.special_lecture_service.domain.common.BaseEntity;
import com.hhplus.special_lecture_service.domain.registration.Registration;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Registration> registrationList;
}
