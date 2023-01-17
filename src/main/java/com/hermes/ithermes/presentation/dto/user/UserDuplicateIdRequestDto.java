package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserDuplicateIdRequestDto {
    @NonNull
    private String id;

    public UserDuplicateIdRequestDto() {

    }

    public UserDuplicateIdRequestDto(@NonNull String id) {
        this.id = id;
    }
}
