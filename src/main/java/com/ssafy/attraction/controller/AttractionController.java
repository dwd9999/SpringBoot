package com.ssafy.attraction.controller;

import com.ssafy.attraction.dto.AttractionDetailResponseDto;
import com.ssafy.attraction.dto.AttractionListRequestDto;
import com.ssafy.attraction.dto.AttractionListResponseDto;
import com.ssafy.attraction.dto.GugunListResponseDto;
import com.ssafy.attraction.model.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/trip")
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAttractionList(AttractionListRequestDto requestDto) {
        List<AttractionListResponseDto> result = attractionService.getAttractionList(requestDto);
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<Map<String, Object>> getAttractionDetail(@PathVariable Integer contentId) {
        AttractionDetailResponseDto result = attractionService.getAttractionDetail(contentId);
        Map<String, Object> response = new HashMap<>();
        response.put("trip", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/gugunlist/{sidoCode}")
    public ResponseEntity<Map<String, Object>> getGugunList(@PathVariable Integer sidoCode) {
        List<GugunListResponseDto> result = attractionService.getGugunList(sidoCode);
        Map<String, Object> response = new HashMap<>();
        response.put("guguns", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
