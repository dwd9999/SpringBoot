package com.ssafy.user.model.service;

import com.ssafy.user.dto.LoginRequestDto;
import com.ssafy.user.dto.LoginResponseDto;
import com.ssafy.user.dto.RegisterRequestDto;

public interface AuthService {

    void register(RegisterRequestDto registerRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);
    
}
