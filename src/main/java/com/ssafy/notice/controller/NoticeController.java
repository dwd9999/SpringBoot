package com.ssafy.notice.controller;

import com.ssafy.board.dto.*;
import com.ssafy.notice.dto.NoticeDetailDto;
import com.ssafy.notice.dto.NoticeListDto;
import com.ssafy.notice.model.service.NoticeService;
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
@RequestMapping("/notice")
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getNoticeList() {
        List<NoticeListDto> result = noticeService.getNoticeList();
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> writeNotice(@RequestBody WriteRequestDto writeRequestDto, @AuthenticationPrincipal User user) {
        noticeService.writeBoard(writeRequestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateNotice(@RequestBody UpdateRequestDto updateRequestDto) {
//        noticeService.updateBoard(updateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNotice(@RequestBody DeleteRequestDto deleteRequestDto) {
//        noticeService.deleteBoard(deleteRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{articleNo}")
    public ResponseEntity<Map<String, Object>> getNoticeDetail(@PathVariable("articleNo") Long articleNo) {
        NoticeDetailDto result = noticeService.getNoticeDetail(articleNo);
        Map<String, Object> response = new HashMap<>();
        response.put("board", result);
        response.put("message", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
