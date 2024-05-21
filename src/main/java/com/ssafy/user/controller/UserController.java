package com.ssafy.user.controller;

import com.ssafy.jwt.exception.UnauthorizedTokenException;
import com.ssafy.jwt.model.service.JwtService;
import com.ssafy.jwt.security.UserAuthorize;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@UserAuthorize
@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getMyInfo(@AuthenticationPrincipal User user) {
        log.info("getMyInfo: {}", user.getUsername());
        UserInfoDto result = userService.getUserInfo(user.getUsername());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable("userId") String userId) {
        UserInfoDto result = userService.getUserInfo(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
