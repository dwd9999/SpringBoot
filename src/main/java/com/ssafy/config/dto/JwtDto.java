package com.ssafy.config.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtDto {
    private final String access_token;
    private final String refresh_token;
}
