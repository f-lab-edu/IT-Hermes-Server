package com.hermes.ithermes.presentation.dto;

import lombok.Getter;

@Getter
public class CommonResponseDto {

    private String message;

    public CommonResponseDto() {
        this.message = "success";
    }
}
