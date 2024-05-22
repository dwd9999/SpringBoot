package com.ssafy.board.model.mapper;

import com.ssafy.board.dto.*;
import org.apache.ibatis.annotations.*;

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

    @Update("update enjoytrips.board " +
            "set subject = #{subject}, content = #{content} " +
            "where article_no = #{articleNo}")
    void updateBoard(UpdateRequestDto updateRequestDto);

    @Delete("delete from enjoytrips.board " +
            "where article_no = #{articleNo}")
    void deleteBoard(DeleteRequestDto deleteRequestDto);
}
