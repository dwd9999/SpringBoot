package com.ssafy.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginPwdCheckDto {
    private final String id;
    private final String password;
    private final String name;
    private final String email;
    private final boolean isAdmin;
    private final boolean flag;
}
