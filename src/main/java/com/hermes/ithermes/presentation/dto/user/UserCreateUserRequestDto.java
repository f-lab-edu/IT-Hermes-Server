package com.hermes.ithermes.presentation.dto.user;

import com.hermes.ithermes.domain.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class UserCreateUserRequestDto {
    @NotBlank(message = "아이디는 필수 입력사항 입니다.")
    private String id;
    @NotBlank(message = "비밀번호는 필수 입력사항 입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
    @NotBlank(message = "비밀번호 확인은 필수 입력사항 입니다.")
    private String passwordConfirm;
    @NotBlank(message = "닉네임은 필수 입력사항 입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자만 가능 합니다.")
    private String nickname;
    @NotBlank(message = "직무는 필수 입력사항 입니다.")
    private String job;
    @NotBlank(message = "경력은 필수 입력사항 입니다.")
    private String experience;

    public UserCreateUserRequestDto(String id, String password, String passwordConfirm, String nickname, String job, String experience) {
        this.id = id;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.nickname = nickname;
        this.job = job;
        this.experience = experience;
    }
}
