package com.ssafy.board.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateRequestDto {
    private final Long articleNo;
    private final String subject;
    private final String content;
}
