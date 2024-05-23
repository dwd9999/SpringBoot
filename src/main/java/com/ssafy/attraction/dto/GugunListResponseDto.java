package com.ssafy.attraction.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GugunListResponseDto {
    private final Integer gugunCode;
    private final String gugunName;
}
