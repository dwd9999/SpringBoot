package com.ssafy.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor
public class BoardDetailDto {
    private final Long articleNo;
    private final String subject;
    private final String userId;
    private final String content;
    private final Long hit;
    private final Long recommendation;
    private final Date date;
}
