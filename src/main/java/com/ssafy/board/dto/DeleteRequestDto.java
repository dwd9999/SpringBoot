package com.ssafy.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class DeleteRequestDto {
    private final Long articleNo;
}
