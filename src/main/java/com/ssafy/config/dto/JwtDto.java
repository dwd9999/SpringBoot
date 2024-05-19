package com.ssafy.config.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@RequiredArgsConstructor
public class JwtDto {
    private final String access_token;
    private final String refresh_token;
}
