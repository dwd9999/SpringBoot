package com.ssafy.attraction.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class AttractionListResponseDto {
    private final Integer contentId;
    private final String title;
    private final String addr1;
    private final String addr2;
    private final String firstImage;
}
