package com.ssafy.notice.model.service;

import com.ssafy.board.dto.BoardDetailDto;
import com.ssafy.board.dto.WriteRequestDto;
import com.ssafy.board.exception.BoardNotFoundException;
import com.ssafy.notice.dto.NoticeDetailDto;
import com.ssafy.notice.dto.NoticeListDto;
import com.ssafy.notice.model.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Override
    public List<NoticeListDto> getNoticeList() {
        return noticeMapper.getNoticeList();
    }

    @Override
    public void writeBoard(WriteRequestDto writeRequestDto, User user) {
        if (!user.getAuthorities().toString().equals("[ADMIN]")) {
            return;
        }

        noticeMapper.writeNotice(writeRequestDto, user.getUsername());
    }

    @Override
    public NoticeDetailDto getNoticeDetail(Long article_no) {
        NoticeDetailDto noticeDetail = noticeMapper.getNoticeDetail(article_no);

        if (noticeDetail == null) {
            throw new BoardNotFoundException();
        }

        return noticeDetail;
    }
}
