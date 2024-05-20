package com.ssafy.user.model.service;

import com.ssafy.config.dto.JwtDto;
import com.ssafy.jwt.model.service.JwtService;
import com.ssafy.user.exception.UserAlreadyExistsException;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.exception.UserWrongPasswordException;
import com.ssafy.user.dto.*;
import com.ssafy.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

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
