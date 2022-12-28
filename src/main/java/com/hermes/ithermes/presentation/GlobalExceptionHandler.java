package com.hermes.ithermes.presentation;

import com.hermes.ithermes.domain.exception.*;
import com.hermes.ithermes.presentation.dto.error.UserErrorDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SameIdException.class)
    public ResponseEntity<String> handleLSameIdException() {
        return new ResponseEntity<>("동일한 아이디의 회원이 존재합니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameNicknameException.class)
    public ResponseEntity<String> handleLSameNicknameException() {
        return new ResponseEntity<>("동일한 닉네임의 회원이 존재합니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnMatchedPasswordException.class)
    public ResponseEntity<String> handleUnMatchedPasswordException() {
        return new ResponseEntity<>("비밀번호와 비밀번호 확인의 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongIdOrPasswordException.class)
    public ResponseEntity<String> handleWrongIdOrPasswordException() {
        return new ResponseEntity<>("아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameUserException.class)
    public ResponseEntity<String> handleSameUserException() {
        return new ResponseEntity<>("이미 존재하는 회원입니다.", HttpStatus.BAD_REQUEST);
    }
}
