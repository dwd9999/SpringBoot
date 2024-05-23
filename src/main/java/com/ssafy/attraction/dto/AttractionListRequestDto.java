package com.ssafy.attraction.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AttractionListRequestDto {
    private final Integer sidoCode;
    private final Integer gugunCode;
    private final Integer contentTypeId;
}
