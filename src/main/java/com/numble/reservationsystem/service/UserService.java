package com.numble.reservationsystem.service;

import com.numble.reservationsystem.config.JwtProvider;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.user.UserRequestDto;
import com.numble.reservationsystem.entity.dto.user.UserResponseDto;
import com.numble.reservationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Value("${spring.jwt.password}")
    private String SECRET_KEY;

    public UserResponseDto register(UserRequestDto requestDto) {
        // 이미 존재하는 유저인지 확인 필요
        User user = User.toEntity(requestDto);
        user.encodePassword(passwordEncoder.encode(requestDto.getPassword()));
        return UserResponseDto.of(userRepository.save(user));
    }



}
