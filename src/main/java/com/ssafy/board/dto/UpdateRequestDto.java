package com.ssafy.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateRequestDto {
    private final Long articleNo;
    private final String subject;
    private final String content;
}
