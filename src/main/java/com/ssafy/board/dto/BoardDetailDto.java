package com.ssafy.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardDetailDto {
    private final Long articleNo;
    private final String subject;
    private final String userId;
    private final String content;
    private final Long hit;
    private final Long recommendation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date date;
}
