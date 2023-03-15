package com.hermes.ithermes.presentation.dto.error;

import lombok.Getter;

@Getter
public class UserErrorDto {
    private String message;
    private String errorData;

    public UserErrorDto(String message, String errorData) {
        this.message = message;
        this.errorData = errorData;
    }
}
