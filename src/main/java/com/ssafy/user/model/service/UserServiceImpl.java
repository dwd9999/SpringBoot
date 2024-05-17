package com.ssafy.user.model.service;

import com.ssafy.user.exception.UserAlreadyExistsException;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.exception.UserWrongPasswordException;
import com.ssafy.user.dto.*;
import com.ssafy.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void register(RegisterRequestDto registerRequestDto) {
        UserInfoDto userInfo = userMapper.getUserInfo(registerRequestDto.getId());
        log.info("Found userInfo: {}", userInfo);

        if (userInfo != null) {
            if (!userInfo.isFlag()) {
                throw new UserAlreadyExistsException();
            }
            log.info("Registered Again user: {}", registerRequestDto);
            userMapper.registerAgain(registerRequestDto);
        }

        log.info("Registered user: {}", registerRequestDto);
        userMapper.register(registerRequestDto);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        LoginPwdCheckDto user = userMapper.login(loginRequestDto.getId());
        log.info("Found user: {}", user);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new UserWrongPasswordException();
        }

        log.info("Logged in user: {}", loginRequestDto);
        return new LoginResponseDto(user.getId(), user.getName(), user.isAdmin(), user.isFlag());
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(String id) {
        UserInfoDto result = userMapper.getUserInfo(id);

        if (result == null) {
            throw new UserNotFoundException();
        }

        return userMapper.getUserInfo(id);
    }
}
