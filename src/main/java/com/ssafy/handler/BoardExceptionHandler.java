package com.ssafy.handler;

import com.ssafy.board.exception.BoardNotFoundException;
import com.ssafy.user.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBoardNotFoundException(BoardNotFoundException e) {
        return responseError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Map<String, String>> responseError(String message, HttpStatus status) {
        return new ResponseEntity<>(Map.of("message", message), status);
    }
}
