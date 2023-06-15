package com.numble.reservationsystem.entity.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private boolean isDeleted;
    private String businessNumber;
}
