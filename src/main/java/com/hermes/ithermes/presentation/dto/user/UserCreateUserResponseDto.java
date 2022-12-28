package com.hermes.ithermes.presentation.dto.user;
import lombok.Getter;

@Getter
public class UserCreateUserResponseDto {
    private String message;

    public UserCreateUserResponseDto(String message) {
        this.message = message;
    }
}
