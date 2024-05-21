package com.ssafy.board.controller;

import com.ssafy.board.dto.BoardDetailDto;
import com.ssafy.board.dto.BoardListDto;
import com.ssafy.board.dto.WriteRequestDto;
import com.ssafy.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardListDto>> getBoardList() {
        List<BoardListDto> result = boardService.getBoardList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{articleNo}")
    public ResponseEntity<BoardDetailDto> getBoardDetail(@PathVariable("articleNo") Long articleNo) {
        BoardDetailDto result = boardService.getBoardDetail(articleNo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> writeBoard(@RequestBody WriteRequestDto writeRequestDto, @AuthenticationPrincipal User user) {
        boardService.writeBoard(writeRequestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}