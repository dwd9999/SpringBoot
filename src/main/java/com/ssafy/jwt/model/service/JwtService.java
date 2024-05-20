package com.ssafy.jwt.model.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.config.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Optional;

public interface JwtService {

    JwtDto createToken(String userId, String email, String role);

    String createAccessToken(String userId, String email, String role);

    String createRefreshToken();

    String validateTokenAndGetUserId(String token);

    String recreateAccessToken(String oldAccessToken);

    void validateRefreshToken(String refreshToken, String oldAccessToken);

    Jws<Claims> validateAndParseToken(String token);

    Claims decodeJwt(String oldAccessToken);
}
