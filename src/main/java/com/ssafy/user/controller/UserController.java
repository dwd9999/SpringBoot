package com.ssafy.user.controller;

import com.ssafy.jwt.security.UserAuthorize;
import com.ssafy.user.dto.*;
import com.ssafy.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getMyInfo(@AuthenticationPrincipal User user) {
        log.info("getMyInfo: {}", user.getUsername());
        UserInfoDto result = userService.getUserInfo(user.getUsername());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable("userId") String userId) {
        UserInfoDto result = userService.getUserInfo(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userInfo", result);
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@AuthenticationPrincipal User user) {
        userService.logout(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> changeUserInfo(@AuthenticationPrincipal User user,
                                                              @RequestBody ChangeRequestDto changeRequestDto) {
        userService.changeUserInfo(user.getUsername(), changeRequestDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteUser(@AuthenticationPrincipal User user) {
        userService.logout(user.getUsername());
        userService.deleteUser(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
