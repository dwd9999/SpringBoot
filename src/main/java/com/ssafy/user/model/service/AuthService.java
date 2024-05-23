package com.ssafy.user.model.service;

import com.ssafy.user.dto.FindPasswordDto;
import com.ssafy.user.dto.LoginRequestDto;
import com.ssafy.user.dto.LoginResponseDto;
import com.ssafy.user.dto.RegisterRequestDto;

public interface AuthService {

    Integer register(RegisterRequestDto registerRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    Integer findPassword(FindPasswordDto findPasswordDto);

    Integer isIdDuplicate(String userId);

}
