package com.ssafy.notice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class NoticeListDto {
    private final Long articleNo;
    private final String userId;
    private final String subject;
    private final Long hit;
    private final Date date;
}
