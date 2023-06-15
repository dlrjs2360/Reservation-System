package com.numble.reservationsystem.service;

import com.numble.reservationsystem.config.JwtProvider;
import com.numble.reservationsystem.entity.Token;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.user.UserRequestDto;
import com.numble.reservationsystem.entity.dto.user.UserResponseDto;
import com.numble.reservationsystem.repository.UserRepository;
import javax.servlet.http.HttpServletResponse;
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

    @Transactional
    public UserResponseDto register(UserRequestDto requestDto) {
        // 이미 존재하는 유저인지 확인 예외처리 필요
        User user = User.toEntity(requestDto);
        user.encodePassword(passwordEncoder.encode(requestDto.getPassword()));
        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto login(UserRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow();

        if (!isValidPassword(requestDto.getPassword(), user.getPassword())) {
            return null; // 예외 처리 추가
        }

        // 액세스, 리프레쉬 토큰 생성
        Token token = jwtProvider.generateJwtToken(user.getEmail(), SECRET_KEY);

        // 헤더에 토큰 저장
        jwtProvider.setHeaderAccessToken(response, token.getAccessToken());
        jwtProvider.setHeaderRefreshToken(response, token.getRefreshToken());

        return UserResponseDto.of(user);
    }

    public UserResponseDto findMemberByEmail(String email) {
        return UserResponseDto.of(userRepository.findByEmail(email).orElseThrow());
    }

    public boolean isValidPassword(String input, String encoded) {
        return passwordEncoder.matches(input, encoded);
    }
}
