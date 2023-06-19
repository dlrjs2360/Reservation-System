package com.numble.reservationsystem.entity.domain;

import com.numble.reservationsystem.entity.UserRole;
import com.numble.reservationsystem.entity.UserState;
import com.numble.reservationsystem.entity.dto.user.UserRequestDto;
import com.numble.reservationsystem.entity.dto.user.UserResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserState userState;

    @Column
    private String businessNumber;

    public void encodePassword(String password) {
        this.password = password;
    }

    public static User toEntity(UserRequestDto requestDto) {
        return User.builder()
            .name(requestDto.getName())
            .email(requestDto.getEmail())
            .phone(requestDto.getPhone())
            .role(requestDto.getRole())
            .userState(requestDto.getUserState())
            .businessNumber(requestDto.getBusinessNumber())
            .build();
    }
}
