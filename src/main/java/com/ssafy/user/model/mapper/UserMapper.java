package com.ssafy.user.model.mapper;

import com.ssafy.user.dto.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into enjoytrips.user (id, password, name, email, isAdmin, flag, joinDate) " +
            "values (#{id}, #{password}, #{name}, #{email}, false, false, now());")
    void register(RegisterRequestDto registerRequestDto);

    @Update("update enjoytrips.user " +
            "set password = #{password}, name = #{name}, email = #{email}, isAdmin = false, flag = false, joinDate = now()" +
            "where id = #{id}")
    void registerAgain(RegisterRequestDto registerRequestDto);

    @Select("select id, password, name, isAdmin, flag " +
            "from enjoytrips.user " +
            "where id = #{id}")
    LoginPwdCheckDto login(@Param("id") String id);


    @Select("select name, email, isAdmin, flag, joinDate " +
            "from enjoytrips.user " +
            "where id = #{id}")
    UserInfoDto getUserInfo(@Param("id") String id);
}
