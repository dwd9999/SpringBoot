package com.ssafy.jwt.model.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@PropertySource("classpath:config.properties")
@Service
public class JwtServiceImpl implements JwtService {

    private final Key key;

    @Value("${ACCESS_TOKEN_EXPIRE}")
    private long accessTokenExpire;
    @Value("${REFRESH_TOKEN_EXPIRE}")
    private long refreshTokenExpire;
    @Value("${ACCESS_TOKEN_HEADER}")
    private String accessTokenHeader;
    @Value("${REFRESH_TOKEN_HEADER}")
    private String refreshTokenHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "access-token";
    private static final String REFRESH_TOKEN_SUBJECT = "refresh-token";
    private static final String USERNAME_CLAIM = "userId";
    private static final String BEARER = "Bearer ";

    public JwtServiceImpl(@Value("${JWT_SECRET}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }


    @Override
    public String createAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
                .claim(USERNAME_CLAIM, userId)
                .signWith(key)
                .compact();
    }

    @Override
    public String createRefreshToken() {
        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpire))
                .signWith(key)
                .compact();
    }

    @Override
    public void updateRefreshToken(String userId, String refreshToken) {

    }

    @Override
    public void destroyRefreshToken(String userId) {

    }

    @Override
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(ACCESS_TOKEN_SUBJECT, accessToken);
        tokenMap.put(REFRESH_TOKEN_SUBJECT, refreshToken);
    }

    @Override
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(ACCESS_TOKEN_SUBJECT, accessToken);
    }

    @Override
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessTokenHeader))
                .filter(accessToken ->
                        accessToken.startsWith(BEARER))
                .map(accessToken ->
                        accessToken.replace(BEARER, ""));
    }

    @Override
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshTokenHeader))
                .filter(refreshToken ->
                        refreshToken.startsWith(BEARER))
                .map(refreshToken ->
                        refreshToken.replace(BEARER, ""));
    }

    @Override
    public Optional<String> extractUserId(String accessToken) {
        try {
            return Optional.ofNullable(Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody().getSubject());
        }
        return Optional.empty();
    }

    @Override
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessTokenHeader, accessToken);
    }

    @Override
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshTokenHeader, refreshToken);
    }

    @Override
    public boolean isTokenValid(String token) {
        return false;
    }
}
