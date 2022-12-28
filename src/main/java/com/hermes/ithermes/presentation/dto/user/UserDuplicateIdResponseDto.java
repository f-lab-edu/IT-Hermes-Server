package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;

@Getter
public class UserDuplicateIdResponseDto {
    private String message;

    public UserDuplicateIdResponseDto(String message) {
        this.message = message;
    }
}
