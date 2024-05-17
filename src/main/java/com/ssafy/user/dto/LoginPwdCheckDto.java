package com.ssafy.user.dto;

import lombok.Data;

@Data
public class LoginPwdCheckDto {
    private final String id;
    private final String password;
    private final String name;
    private final boolean isAdmin;
    private final boolean flag;
}
