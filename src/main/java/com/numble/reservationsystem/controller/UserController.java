package com.numble.reservationsystem.controller;

import com.numble.reservationsystem.entity.dto.user.UserRequestDto;
import com.numble.reservationsystem.entity.dto.user.UserResponseDto;
import com.numble.reservationsystem.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(200).body(userService.register(userRequestDto));
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<UserResponseDto> login(@RequestBody UserRequestDto userRequestDto, @ApiIgnore HttpServletResponse response) {
        return ResponseEntity.status(200).body(userService.login(userRequestDto,response));
    }

    @GetMapping("/profile")
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDto> findMyProfile(@ApiIgnore Authentication authentication) {
        return ResponseEntity.status(200).body(userService.findMemberByEmail(authentication.getName()));
    }

    @GetMapping("/all")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserResponseDto>> findAllUser() {
        return ResponseEntity.status(200).body(userService.findAll());
    }
}
