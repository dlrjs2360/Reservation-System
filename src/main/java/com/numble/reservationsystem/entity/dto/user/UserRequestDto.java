package com.numble.reservationsystem.entity.dto.user;

import com.numble.reservationsystem.entity.UserRole;
import com.numble.reservationsystem.entity.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private UserState userState;
    private String businessNumber;
}
