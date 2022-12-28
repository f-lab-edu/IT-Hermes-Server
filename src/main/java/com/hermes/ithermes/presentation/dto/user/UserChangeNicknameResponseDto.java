package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;

@Getter
public class UserChangeNicknameResponseDto {
    private String message;

    public UserChangeNicknameResponseDto(String message) {
        this.message = message;
    }
}
