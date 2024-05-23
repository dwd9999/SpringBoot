package com.ssafy.handler;

import com.ssafy.jwt.exception.UnauthorizedTokenException;
import com.ssafy.user.exception.UserAlreadyExistsException;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.exception.UserWrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return responseError(e.getDetailMessageArguments()[1].toString().split(":")[1].strip(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return responseError(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e) {
        return responseError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWrongPasswordException.class)
    public ResponseEntity<Map<String, String>> handleUserWrongPasswordException(UserWrongPasswordException e) {
        return responseError(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedTokenException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedTokenException(UnauthorizedTokenException e) {
        return responseError(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        return responseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Map<String, String>> responseError(String message, HttpStatus status) {
        return new ResponseEntity<>(Map.of("message", message), status);
    }
}
