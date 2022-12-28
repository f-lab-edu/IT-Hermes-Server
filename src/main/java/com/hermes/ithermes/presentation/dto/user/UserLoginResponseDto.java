package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {
    private String message;

    public UserLoginResponseDto(String message) {
        this.message = message;
    }
}
