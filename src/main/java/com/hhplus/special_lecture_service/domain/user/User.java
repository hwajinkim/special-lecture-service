package com.hhplus.special_lecture_service.domain.user;

import com.hhplus.special_lecture_service.domain.common.BaseEntity;
import com.hhplus.special_lecture_service.domain.registration.Registration;
import jakarta.persistence.*;
import lombok.*;

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

    public User(Long userId){
        if(!isValidId(userId)){
            throw new IllegalArgumentException("사용자 ID 유효하지 않음.");
        }
        this.id = userId;
    }

    public User(Long userId, String username) {
        this.id = userId;
        this.username = username;
    }

    @Builder
    public User(Long id, String username, List<Registration> registrationList){
        this.id = id;
        this.username = username;
        this.registrationList = registrationList;
    }
    public static boolean isValidId(Long id){
        return id != null &&  id > 0;
    }
}
