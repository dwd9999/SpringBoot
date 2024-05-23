package com.ssafy.notice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class NoticeDetailDto {
    private final Long articleNo;
    private final String subject;
    private final String userId;
    private final String content;
    private final Long hit;
    private final Long recommendation;
    private final Date date;
}
