package com.hermes.ithermes.presentation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDto {
    @NonNull
    private String id;
    @NonNull
    private String password;
}
