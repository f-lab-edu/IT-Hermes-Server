package com.hermes.ithermes.presentation;

import com.hermes.ithermes.domain.exception.NotExistsRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotExistsRequestException.class)
    public ResponseEntity<String> NotExistsRequestValue(){
        return new ResponseEntity("잘못된 request값입니다.", HttpStatus.BAD_REQUEST);
    }


}
