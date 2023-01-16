package com.hermes.ithermes.presentation;

import com.hermes.ithermes.domain.exception.NotExistsRequestParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotExistsRequestParamException.class)
    public ResponseEntity<String> NotExistsRequestValue(){
        return new ResponseEntity("잘못된 요청 파라미터 값입니다.", HttpStatus.BAD_REQUEST);
    }


}
