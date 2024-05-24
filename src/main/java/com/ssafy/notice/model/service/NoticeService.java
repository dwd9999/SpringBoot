package com.ssafy.notice.model.service;

import com.ssafy.board.dto.WriteRequestDto;
import com.ssafy.notice.dto.NoticeDetailDto;
import com.ssafy.notice.dto.NoticeListDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface NoticeService {

    List<NoticeListDto> getNoticeList();

    void writeBoard(WriteRequestDto writeRequestDto);

    NoticeDetailDto getNoticeDetail(Long article_no);
}
