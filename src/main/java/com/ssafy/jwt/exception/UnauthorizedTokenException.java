package com.ssafy.jwt.exception;

public class UnauthorizedTokenException extends RuntimeException {
    public UnauthorizedTokenException() {
        super("사용 불가능한 토큰입니다.");
    }
}
