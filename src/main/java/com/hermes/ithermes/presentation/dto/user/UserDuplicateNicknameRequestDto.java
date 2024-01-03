package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserDuplicateNicknameRequestDto {
    @NonNull
    private String nickname;

    public UserDuplicateNicknameRequestDto() {

    }

    public UserDuplicateNicknameRequestDto(@NonNull String nickname) {
        this.nickname = nickname;
    }
}
