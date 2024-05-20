package com.ssafy.user.model.service;

import com.ssafy.config.dto.JwtDto;
import com.ssafy.jwt.model.service.JwtService;
import com.ssafy.user.dto.*;
import com.ssafy.user.exception.UserAlreadyExistsException;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.exception.UserWrongPasswordException;
import com.ssafy.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void register(RegisterRequestDto registerRequestDto) {
        registerRequestDto.setPassword(encoder.encode(registerRequestDto.getPassword()));
        UserInfoDto userInfo = userMapper.getUserInfo(registerRequestDto.getId());

        if (userInfo != null) {
            if (!userInfo.isFlag()) {
                throw new UserAlreadyExistsException();
            }
            userMapper.registerAgain(registerRequestDto);
        }

        userMapper.register(registerRequestDto);
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        LoginPwdCheckDto user = userMapper.login(loginRequestDto.getId());

        if (user == null || user.isFlag()) {
            throw new UserNotFoundException();
        }

        log.info("request password: {}", loginRequestDto.getPassword());
        log.info("user password: {}", user.getPassword());
        if (!encoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new UserWrongPasswordException();
        }

        JwtDto token = jwtService.createToken(user.getId(), user.getEmail(), user.isAdmin() ? "ADMIN" : "USER");
        userMapper.updateRefreshToken(user.getId(), token.getRefresh_token());

        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.isAdmin(),
                token.getAccess_token(),
                token.getRefresh_token());
    }
}
