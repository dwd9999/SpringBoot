package com.ssafy.board.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class WriteRequestDto {
    private final String id;
    private final String subject;
    private final String content;
}
