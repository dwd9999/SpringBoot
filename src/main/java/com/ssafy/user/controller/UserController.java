package com.ssafy.user.controller;

import com.ssafy.jwt.exception.UnauthorizedTokenException;
import com.ssafy.jwt.model.service.JwtService;
import com.ssafy.user.dto.LoginRequestDto;
import com.ssafy.user.dto.LoginResponseDto;
import com.ssafy.user.dto.RegisterRequestDto;
import com.ssafy.user.dto.UserInfoDto;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequestDto registerRequestDto) {
        log.info("Register Request Arrived! Request: {}", registerRequestDto);
        userService.register(registerRequestDto);
        log.info("Register Success!");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
        log.info("Login Request Arrived! Request: {}", loginRequestDto);
        LoginResponseDto result = userService.login(loginRequestDto);
        result.setAccessToken(jwtService.createAccessToken("userId", result.getId()));
        result.setRefreshToken(jwtService.createRefreshToken("userId", result.getId()));
        log.info("Login Success! Response: {}", result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable("userId") String userId,
                                                   HttpServletRequest request) {
        log.info("Get UserInfo Request Arrived! Request: {}", userId);
        if (!jwtService.checkToken(request.getHeader("access-token"))) {
            throw new UnauthorizedTokenException();
        }
        log.info("Token Checked!");

        UserInfoDto result = userService.getUserInfo(userId);

        if (result == null) {
            throw new UserNotFoundException();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
