package com.hermes.ithermes.presentation;

import com.hermes.ithermes.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotExistsRequestParamException.class)
    public ResponseEntity<String> NotExistsRequestValue(){
        return new ResponseEntity("잘못된 요청 파라미터 값입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameIdException.class)
    public ResponseEntity<String> handleLSameIdException() {
        return new ResponseEntity<>("이미 존재하는 아이디", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameNicknameException.class)
    public ResponseEntity<String> handleLSameNicknameException() {
        return new ResponseEntity<>("동일한 닉네임 회원 존재", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnMatchedPasswordException.class)
    public ResponseEntity<String> handleUnMatchedPasswordException() {
        return new ResponseEntity<>("비밀번호와 비밀번호 확인 데이터 불일치", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongIdOrPasswordException.class)
    public ResponseEntity<String> handleWrongIdOrPasswordException() {
        return new ResponseEntity<>("아이디 또는 비밀번호 불일치", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameUserException.class)
    public ResponseEntity<String> handleSameUserException() {
        return new ResponseEntity<>("이미 존재하는 회원", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EnumTypeFormatException.class)
    public ResponseEntity<String> enumTypeFormatException() {
        return new ResponseEntity<>("서버에 존재하지 않는 유효하지 않는 데이터", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
