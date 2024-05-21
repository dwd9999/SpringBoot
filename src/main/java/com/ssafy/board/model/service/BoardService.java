package com.ssafy.board.model.service;

import com.ssafy.board.dto.BoardDetailDto;
import com.ssafy.board.dto.BoardListDto;
import com.ssafy.board.dto.WriteRequestDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface BoardService {

    List<BoardListDto> getBoardList();

    BoardDetailDto getBoardDetail(Long article_no);

    void writeBoard(WriteRequestDto writeRequestDto, User user);
}
