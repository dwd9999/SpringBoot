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
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

//    @GetMapping("/info/{userId}")
//    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable("userId") String userId,
//                                                   HttpServletRequest request) {
//        if (!jwtService.checkToken(request.getHeader("access-token"))) {
//            throw new UnauthorizedTokenException();
//        }
//
//        UserInfoDto result = userService.getUserInfo(userId);
//
//        if (result == null) {
//            throw new UserNotFoundException();
//        }
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
