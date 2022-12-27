package com.hermes.ithermes.presentation.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;

    private String nickname;

    private String userId;

    private String password;

    private String job;

    private int experience;

    private boolean isDelete;

    public UserResponseDto(Long id, String nickname, String userId, String password, String job, int experience, boolean isDelete) {
        this.id = id;
        this.nickname = nickname;
        this.userId = userId;
        this.password = password;
        this.job = job;
        this.experience = experience;
        this.isDelete = isDelete;
    }
}
