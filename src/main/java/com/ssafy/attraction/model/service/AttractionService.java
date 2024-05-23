package com.ssafy.attraction.model.service;

import com.ssafy.attraction.dto.AttractionDetailResponseDto;
import com.ssafy.attraction.dto.AttractionListRequestDto;
import com.ssafy.attraction.dto.AttractionListResponseDto;
import com.ssafy.attraction.dto.GugunListResponseDto;

import java.util.List;

public interface AttractionService {
    List<AttractionListResponseDto> getAttractionList(AttractionListRequestDto requestDto);

    AttractionDetailResponseDto getAttractionDetail(Integer contentId);

    List<GugunListResponseDto> getGugunList(Integer sidoCode);
}
