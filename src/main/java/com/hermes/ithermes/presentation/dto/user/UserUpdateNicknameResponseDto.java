package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserUpdateNicknameResponseDto {
    @NonNull
    private String message;

    public UserUpdateNicknameResponseDto(String message) {
        this.message = message;
    }
}
