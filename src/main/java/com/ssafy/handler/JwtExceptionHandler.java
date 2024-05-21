package com.ssafy.handler;

import com.ssafy.jwt.exception.UnauthorizedTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        return new ResponseEntity<>("토큰이 만료되었습니다.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException e) {
        return new ResponseEntity<>("토큰이 손상되었습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedTokenException.class)
    public ResponseEntity<String> handleUnauthorizedTokenException(UnauthorizedTokenException e) {
        return new ResponseEntity<>("허용되지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException e) {
        return new ResponseEntity<>("JWT 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
