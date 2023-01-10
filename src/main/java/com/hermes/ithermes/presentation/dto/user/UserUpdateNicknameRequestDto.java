package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserUpdateNicknameRequestDto {
    @NonNull
    private String id;
    @NonNull
    private String nickname;

    public UserUpdateNicknameRequestDto() {

    }

    public UserUpdateNicknameRequestDto(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
