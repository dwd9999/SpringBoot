package com.ssafy.notice.model.mapper;

import com.ssafy.board.dto.WriteRequestDto;
import com.ssafy.notice.dto.NoticeDetailDto;
import com.ssafy.notice.dto.NoticeListDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("select article_no, user_id, subject, hit, date " +
            "from enjoytrips.board " +
            "where isnotice = true " +
            "order by article_no desc")
    List<NoticeListDto> getNoticeList();

    @Select("select article_no, subject, user_id, content, hit, recommendation, date " +
            "from enjoytrips.board " +
            "where article_no = #{article_no} and isnotice = true")
    NoticeDetailDto getNoticeDetail(Long article_no);

    @Insert("insert into enjoytrips.board(subject, user_id, content, hit, recommendation, comment, date, isnotice) " +
            "value (#{writeRequestDto.subject}, #{userId}, #{writeRequestDto.content}, 0, 0, 0, now(), true)")
    void writeNotice(WriteRequestDto writeRequestDto, String userId);
}
