package com.ssafy.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException() {
        return new ResponseEntity<>("손상된 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException() {
        return new ResponseEntity<>("토큰 처리 과정에서 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
