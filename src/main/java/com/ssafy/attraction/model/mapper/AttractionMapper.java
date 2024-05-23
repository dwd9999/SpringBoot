package com.ssafy.attraction.model.mapper;

import com.ssafy.attraction.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttractionMapper {

    @Select("select content_id, title, addr1, addr2, first_image2 " +
            "from enjoytrips.attraction_info " +
            "where content_type_id = #{contentTypeId} and sido_code = #{sidoCode} and gugun_code = #{gugunCode}")
    List<AttractionListResponseDto> getAttractionList(AttractionListRequestDto requestDto);

    @Select("select title, addr1, addr2, zipcode, tel, first_image, latitude, longitude " +
            "from enjoytrips.attraction_info " +
            "where content_id = #{contentId}")
    AttractionInfoDetail getAttractionDetail(Integer contentId);

    @Select("select overview " +
            "from enjoytrips.attraction_description " +
            "where content_id = #{contentId}")
    String getAttractionOverview(Integer contentId);

    @Select("select gugun_code, gugun_name " +
            "from enjoytrips.gugun " +
            "where sido_code = #{sidoCode}")
    List<GugunListResponseDto> getGugunList(Integer sidoCode);
}
