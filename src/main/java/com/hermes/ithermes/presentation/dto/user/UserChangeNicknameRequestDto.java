package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserChangeNicknameRequestDto {
    @NonNull
    private String id;
    @NonNull
    private String nickname;

    public UserChangeNicknameRequestDto(@NonNull String id, @NonNull String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
