package com.ssafy.user.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private final String id;
    private final String name;
    private final boolean isAdmin;
    private final boolean flag;
    private String accessToken;
    private String refreshToken;
}
