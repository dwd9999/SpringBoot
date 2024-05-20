package com.ssafy.filter;

import com.ssafy.jwt.model.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Order(0)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final String accessTokenHeader = "access-token";
    private final String refreshTokenHeader = "refresh-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = parseBearerToken(request, accessTokenHeader);
            User user = parseUserSpecification(accessToken);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, accessToken, user.getAuthorities());
            authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticated);
        } catch (ExpiredJwtException e) {
            log.info("JWT Token Refresh");
            reissueAccessToken(request, response, e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request, String headerName) {
        log.info("Get Token From Header");
        return Optional.ofNullable(request.getHeader(headerName))
                .filter(token ->
                        token.startsWith("Bearer "))
                .map(token ->
                        token.substring(7))
                .orElse(null);
    }

    private User parseUserSpecification(String token) {
        log.info("Get UserInfo From Token");
        String[] split = Optional.ofNullable(token)
                .filter(subject ->
                        subject.length() >= 10)
                .map(jwtService::validateTokenAndGetSubject)
                .orElse("anonymous:anonymous")
                .split(":");

        return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
    }

    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException exception) {
        String refreshToken = parseBearerToken(request, refreshTokenHeader);
        if (refreshToken == null) {
            throw exception;
        }
        String oldAccessToken = parseBearerToken(request, accessTokenHeader);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

