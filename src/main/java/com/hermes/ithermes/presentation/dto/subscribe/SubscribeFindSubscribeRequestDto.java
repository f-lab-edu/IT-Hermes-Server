package com.hermes.ithermes.presentation.dto.subscribe;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubscribeFindSubscribeRequestDto {
    @NotBlank(message = "아이디는 필수 입력사항 입니다.")
    private String id;
}
