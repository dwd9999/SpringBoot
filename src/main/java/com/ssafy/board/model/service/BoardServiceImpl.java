package com.ssafy.board.model.service;

import com.ssafy.board.dto.BoardDetailDto;
import com.ssafy.board.dto.BoardListDto;
import com.ssafy.board.dto.WriteRequestDto;
import com.ssafy.board.exception.BoardNotFoundException;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.jwt.model.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final JwtService jwtService;

    @Override
    public List<BoardListDto> getBoardList() {
        return boardMapper.getBoardList();
    }

    @Override
    public BoardDetailDto getBoardDetail(Long article_no) {
        BoardDetailDto boardDetail = boardMapper.getBoardDetail(article_no);

        if (boardDetail == null) {
            throw new BoardNotFoundException();
        }

        return boardDetail;
    }

    @Override
    public void writeBoard(WriteRequestDto writeRequestDto, User user) {
        if (user.getAuthorities().toString().equals("[ADMIN]")) {
            log.info("공지사항 작성");
            boardMapper.writeNotice(writeRequestDto, user.getUsername());
        } else {
            log.info("게시글 작성");
            boardMapper.writeBoard(writeRequestDto, user.getUsername());
        }
    }
}
