package com.numble.reservationsystem.service;

import com.numble.reservationsystem.config.JwtProvider;
import com.numble.reservationsystem.entity.Token;
import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.entity.dto.user.UserRequestDto;
import com.numble.reservationsystem.entity.dto.user.UserResponseDto;
import com.numble.reservationsystem.exception.CustomException;
import com.numble.reservationsystem.exception.handler.ErrorCode;
import com.numble.reservationsystem.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
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
        if (userRepository.existsByEmail(requestDto.getEmail())){
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = User.toEntity(requestDto);
        user.encodePassword(passwordEncoder.encode(requestDto.getPassword()));
        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto login(UserRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(requestDto.getEmail());
        isValidPassword(requestDto.getPassword(), user.getPassword());

        Token token = jwtProvider.generateJwtToken(user.getEmail(), SECRET_KEY);
        jwtProvider.setHeaderAccessToken(response, token.getAccessToken());
        jwtProvider.setHeaderRefreshToken(response, token.getRefreshToken());

        return UserResponseDto.of(user);
    }

    public UserResponseDto findMemberByEmail(String email) {
        return UserResponseDto.of(userRepository.findByEmail(email));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserResponseDto::of)
            .collect(Collectors.toList());
    }

    public void isValidPassword(String input, String encoded) {
        if (!passwordEncoder.matches(input, encoded)){
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
