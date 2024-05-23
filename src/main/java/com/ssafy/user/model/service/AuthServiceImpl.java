package com.ssafy.user.model.service;

import com.ssafy.jwt.dto.JwtDto;
import com.ssafy.jwt.model.service.JwtService;
import com.ssafy.mail.model.service.MailService;
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
import org.springframework.validation.annotation.Validated;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final MailService mailService;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void register(RegisterRequestDto registerRequestDto) {
        UserInfoDto userInfo = userMapper.getUserInfo(registerRequestDto.getId());

        if (userInfo != null) {
            if (!userInfo.isFlag()) {
                throw new UserAlreadyExistsException();
            }
            userMapper.registerAgain(registerRequestDto, encoder.encode(registerRequestDto.getPassword()));
        } else {
            userMapper.register(registerRequestDto, encoder.encode(registerRequestDto.getPassword()));
        }
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        LoginPwdCheckDto user = userMapper.login(loginRequestDto.getId());

        if (user == null || user.isFlag()) {
            throw new UserNotFoundException();
        }

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
                token.getRefresh_token(),
                "success");
    }

    @Override
    public Integer findPassword(FindPasswordDto findPasswordDto) {
        UserInfoDto userInfo = userMapper.getUserInfo(findPasswordDto.getUserId());

        if (userInfo == null || userInfo.isFlag() || !userInfo.getEmail().equals(findPasswordDto.getEmail())) {
            return 0;
        }

        Random random = new Random();
        String generatedPassword = random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(13)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        mailService.sendNewPassword(userInfo.getEmail(), generatedPassword);
        userMapper.changePassword(findPasswordDto.getUserId(), encoder.encode(generatedPassword));

        return 1;

    }

    @Override
    public Integer isIdDuplicate(String userId) {
        UserInfoDto result = userMapper.getUserInfo(userId);
        if (result == null) {
            return 0;
        } else {
            return 1;
        }
    }
}
