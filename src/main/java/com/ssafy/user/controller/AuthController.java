package com.ssafy.user.controller;

import com.ssafy.user.dto.FindPasswordDto;
import com.ssafy.user.dto.LoginRequestDto;
import com.ssafy.user.dto.LoginResponseDto;
import com.ssafy.user.dto.RegisterRequestDto;
import com.ssafy.user.model.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequestDto registerRequestDto) {
        authService.register(registerRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
        LoginResponseDto result = authService.login(loginRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/checkId/{userId}")
    public ResponseEntity<Integer> checkId(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(authService.isIdDuplicate(userId), HttpStatus.OK);
    }

    @PostMapping("/lost")
    public ResponseEntity<Integer> findPassword(@RequestBody @Validated FindPasswordDto findPasswordDto) {
        return new ResponseEntity<>(authService.findPassword(findPasswordDto), HttpStatus.OK);
    }
}
