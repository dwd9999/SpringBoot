package com.ssafy.board.controller;

import com.ssafy.board.dto.*;
import com.ssafy.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBoardList() {
        List<BoardListDto> result = boardService.getBoardList();
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> writeBoard(@RequestBody WriteRequestDto writeRequestDto) {
        boardService.writeBoard(writeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateBoard(@RequestBody UpdateRequestDto updateRequestDto) {
        boardService.updateBoard(updateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteBoard(@RequestBody DeleteRequestDto deleteRequestDto) {
        boardService.deleteBoard(deleteRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{articleNo}")
    public ResponseEntity<Map<String, Object>> getBoardDetail(@PathVariable("articleNo") Long articleNo) {
        BoardDetailDto result = boardService.getBoardDetail(articleNo);
        Map<String, Object> response = new HashMap<>();
        response.put("board", result);
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
