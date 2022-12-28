package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;

@Getter
public class UserDuplicateNicknameResponseDto {
    private String message;

    public UserDuplicateNicknameResponseDto(String message) {
        this.message = message;
    }
}
