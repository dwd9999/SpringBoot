package com.ssafy.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardListDto {
    private final Long articleNo;
    private final String userId;
    private final String subject;
    private final Long hit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date date;
}
