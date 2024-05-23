package com.ssafy.attraction.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AttractionInfoDetail {
    private final String title;
    private final String addr1;
    private final String addr2;
    private final String zip;
    private final String tel;
    private final String firstImage2;
    private final double latitude;
    private final double longitude;


}
