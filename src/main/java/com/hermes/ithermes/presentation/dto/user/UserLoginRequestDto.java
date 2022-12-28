package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserLoginRequestDto {
    @NonNull
    private String id;
    @NonNull
    private String password;

    public UserLoginRequestDto(@NonNull String id, @NonNull String password) {
        this.id = id;
        this.password = password;
    }
}
