package com.ssafy.user.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private final String id;
    private final String name;
    private final boolean isAdmin;
    private String accessToken;
    private String refreshToken;
    private String message;
}
