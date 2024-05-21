package com.ssafy.user.controller;

import com.ssafy.user.dto.FindPasswordDto;
import com.ssafy.user.dto.LoginRequestDto;
import com.ssafy.user.dto.LoginResponseDto;
import com.ssafy.user.dto.RegisterRequestDto;
import com.ssafy.user.model.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequestDto registerRequestDto) {
        authService.register(registerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
        LoginResponseDto result = authService.login(loginRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/lost")
    public ResponseEntity<?> findPassword(@RequestBody @Validated FindPasswordDto findPasswordDto) {
        authService.findPassword(findPasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
