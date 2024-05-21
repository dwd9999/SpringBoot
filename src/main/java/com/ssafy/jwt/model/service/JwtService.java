package com.ssafy.jwt.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.jwt.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Map;

public interface JwtService {

    JwtDto createToken(String userId, String email, String role);

    String createAccessToken(String userId, String email, String role);

    String createRefreshToken();

    String validateTokenAndGetUserInfo(String token);

    String recreateAccessToken(String oldAccessToken) throws JsonProcessingException;

    void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException;

    Jws<Claims> validateAndParseToken(String token);

    Map<String, Object> decodeJwt(String oldAccessToken) throws JsonProcessingException;
}
