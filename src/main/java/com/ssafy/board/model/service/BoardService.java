package com.ssafy.board.model.service;

import com.ssafy.board.dto.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface BoardService {

    List<BoardListDto> getBoardList();

    void writeBoard(WriteRequestDto writeRequestDto);

    void updateBoard(UpdateRequestDto updateRequestDto);

    void deleteBoard(DeleteRequestDto deleteRequestDto);

    BoardDetailDto getBoardDetail(Long article_no);
}
