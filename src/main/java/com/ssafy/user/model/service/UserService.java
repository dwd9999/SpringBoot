package com.ssafy.user.model.service;

import com.ssafy.user.dto.*;

public interface UserService {
    void register(RegisterRequestDto registerRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserInfoDto getUserInfo(String id);

}
