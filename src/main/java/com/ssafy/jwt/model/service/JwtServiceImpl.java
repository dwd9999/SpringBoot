package com.ssafy.jwt.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.jwt.dto.JwtDto;
import com.ssafy.user.model.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@PropertySource("classpath:config.properties")
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final UserMapper userMapper;

    @Value("${JWT_SECRET}")
    private String secret;
    @Value("${ACCESS_TOKEN_EXPIRE}")
    private long accessTokenExpire;
    @Value("${REFRESH_TOKEN_EXPIRE}")
    private long refreshTokenExpire;
    @Value("${ACCESS_TOKEN_HEADER}")
    private String accessTokenHeader;
    @Value("${REFRESH_TOKEN_HEADER}")
    private String refreshTokenHeader;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ACCESS_TOKEN_SUBJECT = "access-token";
    private static final String REFRESH_TOKEN_SUBJECT = "refresh-token";


    @Override
    public JwtDto createToken(String userId, String email, String role) {
        String accessToken = createAccessToken(userId, email, role);
        String refreshToken = createRefreshToken();

        return new JwtDto(accessToken, refreshToken);
    }

    @Override
    public String createAccessToken(String userId, String email, String role) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    @Override
    public String createRefreshToken() {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpire))
                .signWith(key)
                .compact();
    }

    @Override
    public String validateTokenAndGetUserInfo(String token) {
        Claims claims = validateAndParseToken(token).getBody();
        return claims.get("userId") + ":" + claims.get("role");
    }

    @Override
    @Transactional
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        Map<String, Object> claims = decodeJwt(oldAccessToken);
        String userId = claims.get("userId").toString();
        String email = claims.get("email").toString();
        String role = claims.get("role").toString();

        String savedRefreshToken = userMapper.findRefreshTokenByUserId(userId);
        if (savedRefreshToken == null) {
            throw new ExpiredJwtException(null, null, "만료된 토큰입니다.");
        }

        return createAccessToken(userId, email, role);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String userId = decodeJwt(oldAccessToken).get("userId").toString();
        String savedRefreshToken = userMapper.findRefreshTokenByUserId(userId);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new ExpiredJwtException(null, null, "만료된 토큰입니다.");
        }
    }

    @Override
    public Jws<Claims> validateAndParseToken(String token) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    @Override
    public Map<String, Object> decodeJwt(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(
                        oldAccessToken.split("\\.")[1]),
                        StandardCharsets.UTF_8), Map.class);
    }


}
