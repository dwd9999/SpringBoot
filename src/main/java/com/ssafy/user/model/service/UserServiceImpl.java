package com.ssafy.user.model.service;

import com.ssafy.jwt.model.service.JwtService;
import com.ssafy.user.exception.UserNotFoundException;
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

        if (result == null || result.isFlag()) {
            throw new UserNotFoundException();
        }

        return userMapper.getUserInfo(id);
    }

    @Override
    @Transactional
    public void logout(String userId) {
        userMapper.destroyRefreshToken(userId);
    }

    @Override
    @Transactional
    public void changeUserInfo(String userId, ChangeRequestDto changeRequestDto) {
        userMapper.changeUserInfo(changeRequestDto, userId);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        userMapper.deleteUser(userId);
    }
}
