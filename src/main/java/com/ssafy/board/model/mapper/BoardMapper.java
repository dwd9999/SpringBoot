package com.ssafy.board.model.mapper;

import com.ssafy.board.dto.BoardDetailDto;
import com.ssafy.board.dto.BoardListDto;
import com.ssafy.board.dto.WriteRequestDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select("select article_no, user_id, subject, hit, date " +
            "from enjoytrips.board " +
            "where isnotice = false " +
            "order by article_no desc")
    List<BoardListDto> getBoardList();

    @Select("select article_no, user_id, subject, hit, date " +
            "from enjoytrips.board " +
            "where isnotice = true " +
            "order by article_no desc")
    List<BoardListDto> getNoticeList();

    @Select("select article_no, subject, user_id, content, hit, recommendation, date " +
            "from enjoytrips.board " +
            "where article_no = #{article_no}")
    BoardDetailDto getBoardDetail(Long article_no);

    @Insert("insert into enjoytrips.board(subject, user_id, content, hit, recommendation, comment, date, isnotice) " +
            "value (#{writeRequestDto.subject}, #{userId}, #{writeRequestDto.content}, 0, 0, 0, now(), false)")
    void writeBoard(WriteRequestDto writeRequestDto, String userId);

    @Insert("insert into enjoytrips.board(subject, user_id, content, hit, recommendation, comment, date, isnotice) " +
            "value (#{writeRequestDto.subject}, #{userId}, #{writeRequestDto.content}, 0, 0, 0, now(), true)")
    void writeNotice(WriteRequestDto writeRequestDto, String userId);
}
