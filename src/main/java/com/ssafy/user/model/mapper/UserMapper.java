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

    @Insert("insert into enjoytrips.user (id, password, name, email, isAdmin, flag, joinDate) " +
            "values (#{id}, #{password}, #{name}, #{email}, true, false, now()) " +
            "on duplicate key update password = #{password}, name = #{name}, email = #{email}, flag = false, joinDate = now();")
    void registerAdmin(String id, String password, String name, String email);

    @Select("select id, password, name, email, isAdmin, flag " +
            "from enjoytrips.user " +
            "where id = #{id}")
    LoginPwdCheckDto login(String id);


    @Select("select name, email, isAdmin, flag, joinDate " +
            "from enjoytrips.user " +
            "where id = #{id}")
    UserInfoDto getUserInfo(String id);

    @Insert("insert into enjoytrips.user_token (user_id, token) " +
            "values (#{userId}, #{refreshToken}) " +
            "on duplicate key update user_id = #{userId}, token = #{refreshToken}")
    void updateRefreshToken(String userId, String refreshToken);

    @Update("update enjoytrips.user_token " +
            "set token = null " +
            "where user_id = ${userId}")
    void destroyRefreshToken(String userId);

    @Select("select token " +
            "from enjoytrips.user_token " +
            "where user_id = #{userId}")
    String findRefreshTokenByUserId(String userId);

    @Select("select user_id " +
            "from enjoytrips.user_token " +
            "where token = #{refreshToken}")
    String findByRefreshToken(String refreshToken);



}
