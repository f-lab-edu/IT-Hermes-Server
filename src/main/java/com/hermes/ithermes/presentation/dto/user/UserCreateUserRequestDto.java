package com.hermes.ithermes.presentation.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class UserCreateUserRequestDto {
    @NonNull
    @NotBlank(message = "아이디는 필수 입력사항 입니다.")
    private String id;
    @NonNull
    @NotBlank(message = "비밀번호는 필수 입력사항 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
    @NonNull
    @NotBlank(message = "비밀번호 확인은 필수 입력사항 입니다.")
    private String passwordConfirm;
    @NonNull
    @NotBlank(message = "닉네임은 필수 입력사항 입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자만 가능 합니다.")
    private String nickname;
    @NonNull
    @NotBlank(message = "직무는 필수 입력사항 입니다.")
    private String job;
    @NonNull
    @NotBlank(message = "경력은 필수 입력사항 입니다.")
    private String yearOfExperience;
    /** 키워드는 최대 5개, 5개 이하 일 시, 배열에 null 삽입*/
    @NonNull
    @NotEmpty(message = "키워드는 필수 입력사항 입니다.")
    private String[] keywordList;
}
