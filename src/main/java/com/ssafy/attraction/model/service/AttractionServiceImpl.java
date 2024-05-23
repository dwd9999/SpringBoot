package com.ssafy.attraction.model.service;

import com.ssafy.attraction.dto.*;
import com.ssafy.attraction.model.mapper.AttractionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;

    @Override
    public List<AttractionListResponseDto> getAttractionList(AttractionListRequestDto requestDto) {
        return attractionMapper.getAttractionList(requestDto);
    }

    @Override
    public AttractionDetailResponseDto getAttractionDetail(Integer contentId) {
        AttractionInfoDetail attractionDetail = attractionMapper.getAttractionDetail(contentId);
        String overview = attractionMapper.getAttractionOverview(contentId);
        return new AttractionDetailResponseDto(attractionDetail, overview);
    }

    @Override
    public List<GugunListResponseDto> getGugunList(Integer sidoCode) {
        return attractionMapper.getGugunList(sidoCode);
    }
}
