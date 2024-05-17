package com.ssafy.jwt.model.service;

import java.util.Map;

public interface JwtService {

    /**
     * Access Token 요청 메서드
     *
     * @param key:  "userId"
     * @param data: 실제 사용자 아이디
     * @return {@link #create(String, Object, String, long)} 메서드로 토큰 종류와 만료 시간을 포함하여 전달
     */
    <T> String createAccessToken(String key, T data);

    /**
     * Refresh Token 요청 메서드
     *
     * @param key:  "userId"
     * @param data: 실제 사용자 아이디
     * @return {@link #create(String, Object, String, long)} 메서드로 토큰 종류와 만료 시간을 포함하여 전달
     */
    <T> String createRefreshToken(String key, T data);

    /**
     * JWT Token 생성 메서드
     *
     * @param key:     "userId"
     * @param data:    실제 사용자 아이디
     * @param subject: Token 종류
     * @param expire:  만료 시간
     * @return JWT Token
     */
    <T> String create(String key, T data, String subject, long expire);

    Map<String, Object> get();

    /**
     * {@link #get()} 메서드로
     * @return
     */
    String getUserId();

    boolean checkToken(String token);

}
