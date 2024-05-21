package com.ssafy.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor
public class BoardListDto {
    private final Long articleNo;
    private final String userId;
    private final String subject;
    private final Long hit;
    private final Date date;
}
