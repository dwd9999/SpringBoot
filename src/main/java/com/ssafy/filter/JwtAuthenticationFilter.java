package com.ssafy.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.jwt.model.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
            String accessToken = request.getHeader(accessTokenHeader);
            User user = parseUserSpecification(accessToken);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, accessToken, user.getAuthorities());
            authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticated);
        } catch (ExpiredJwtException e) {
            log.info("JWT Token Refresh");
            request.setAttribute("exception", "토큰이 만료되었습니다.");
            reissueAccessToken(request, response, e);
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "토큰이 손상되었습니다.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "허용되지 않은 토큰입니다.");
        } catch (JwtException e) {
            request.setAttribute("exception", "JWT 에러 발생");
        }

        filterChain.doFilter(request, response);
    }

    private User parseUserSpecification(String token) {
        String[] split = Optional.ofNullable(token)
                .map(jwtService::validateTokenAndGetUserInfo)
                .orElse("anonymous:anonymous")
                .split(":");

        return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
    }

    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException exception) {
        try {
            String refreshToken = request.getHeader(refreshTokenHeader);
            if (refreshToken == null) {
                throw exception;
            }
            String oldAccessToken = request.getHeader(accessTokenHeader);
            jwtService.validateRefreshToken(refreshToken, oldAccessToken);
            String newAccessToken = jwtService.recreateAccessToken(oldAccessToken);
            User user = parseUserSpecification(newAccessToken);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, newAccessToken, user.getAuthorities());
            authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            response.setHeader("new-access-token", newAccessToken);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "토큰이 손상되었습니다.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "허용되지 않은 토큰입니다.");
        } catch (JwtException e) {
            request.setAttribute("exception", "JWT 에러 발생");
        } catch (JsonProcessingException e) {
            request.setAttribute("exception", "JSON 변환 에러 발생");
        }
    }
}

