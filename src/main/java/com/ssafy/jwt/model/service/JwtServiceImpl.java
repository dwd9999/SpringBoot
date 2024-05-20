package com.ssafy.jwt.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.config.dto.JwtDto;
import com.ssafy.user.dto.UserInfoDto;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.model.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

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
    public String validateTokenAndGetUserId(String token) {
        return validateAndParseToken(token)
                .getBody()
                .get("userId", String.class);
    }

    @Override
    @Transactional
    public String recreateAccessToken(String oldAccessToken) {
        Claims claims = decodeJwt(oldAccessToken);
        String userId = claims.get("userId", String.class);
        String email = claims.get("email", String.class);
        String role = claims.get("role", String.class);

        String savedRefreshToken = userMapper.findRefreshTokenByUserId(userId);
        if (savedRefreshToken == null) {
            throw new ExpiredJwtException(null, null, "만료된 토큰입니다.");
        }

        return createAccessToken(userId, email, role);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateRefreshToken(String refreshToken, String oldAccessToken) {
        validateAndParseToken(refreshToken);
        String userId = decodeJwt(oldAccessToken).get("userId", String.class);
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
    public Claims decodeJwt(String oldAccessToken) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(oldAccessToken)
                .getBody();
    }


}
