package com.hermes.ithermes.presentation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserCheckRefreshTokenResponseDto {
    private String message;
    private String accessToken;
}
