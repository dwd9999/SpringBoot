package com.ssafy.user.exception;

public class UserWrongPasswordException extends RuntimeException {
    public UserWrongPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
