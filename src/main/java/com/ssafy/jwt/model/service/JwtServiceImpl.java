package com.ssafy.jwt.model.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
@PropertySource("classpath:config.properties")
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${JWT_SALT}")
    private static String SALT;
    private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 30;
    private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 60;

    @Override
    public <T> String createAccessToken(String key, T data) {
        return create(key, data, "access-token", 1000 * 60 * ACCESS_TOKEN_EXPIRE_MINUTES);
    }

    @Override
    public <T> String createRefreshToken(String key, T data) {
        return create(key, data, "refresh-token", 1000 * 60 * 3 * REFRESH_TOKEN_EXPIRE_MINUTES);
    }

    @Override
    public <T> String create(String key, T data, String subject, long expire) {
        log.info("Create Claims Start");
        Claims claims = Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000));

        claims.put(key, data);
        log.info("Claims: {}", claims);

        return Jwts.builder().setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SALT);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Map<String, Object> get() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("access-token");
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SALT).build().parseClaimsJws(jwt);
        return claims.getBody();
    }

    @Override
    public String getUserId() {
        return (String) get().get("userId");
    }

    @Override
    public boolean checkToken(String token) {
        if (token == null || token.isEmpty()) {

        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token);
            log.debug("claims : {}", claims);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
