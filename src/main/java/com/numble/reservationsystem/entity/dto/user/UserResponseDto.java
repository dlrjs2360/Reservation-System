package com.numble.reservationsystem.entity.dto.user;

import com.numble.reservationsystem.entity.domain.User;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private boolean isDeleted;
    private String businessNumber;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .phone(user.getPhone())
            .role(user.getRole())
            .isDeleted(user.isDeleted())
            .businessNumber(user.getBusinessNumber())
            .build();
    }

}
