package com.ssafy.attraction.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AttractionDetailResponseDto {
    private final String title;
    private final String addr1;
    private final String addr2;
    private final String zip;
    private final String tel;
    private final String firstImage2;
    private final double latitude;
    private final double longitude;
    private final String overview;

    public AttractionDetailResponseDto(AttractionInfoDetail detail, String detailOverview) {
        title = detail.getTitle();
        addr1 = detail.getAddr1();
        addr2 = detail.getAddr2();
        zip = detail.getZip();
        tel = detail.getTel();
        firstImage2 = detail.getFirstImage2();
        latitude = detail.getLatitude();
        longitude = detail.getLongitude();
        overview = detailOverview;
    }
}
