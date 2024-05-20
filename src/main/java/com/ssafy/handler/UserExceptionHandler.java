package com.ssafy.handler;

import com.ssafy.jwt.exception.UnauthorizedTokenException;
import com.ssafy.user.exception.UserAlreadyExistsException;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.exception.UserWrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWrongPasswordException.class)
    public ResponseEntity<String> handleUserWrongPasswordException(UserWrongPasswordException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedTokenException.class)
    public ResponseEntity<String> handleUnauthorizedTokenException(UnauthorizedTokenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>("오류가 발생했습니다. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
