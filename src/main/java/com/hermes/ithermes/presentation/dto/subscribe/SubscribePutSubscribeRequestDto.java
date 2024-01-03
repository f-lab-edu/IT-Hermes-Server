package com.hermes.ithermes.presentation.dto.subscribe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubscribePutSubscribeRequestDto {

    @NotBlank(message = "아이디는 필수 입력사항 입니다.")
    private String id;

    /**
     * 서비스 구독 활성화 정보, 비활성화 시, 배열에 "NON_ACTIVE" 삽입
     */
    @NotEmpty(message = "서비스정보는 필수 입력사항 입니다.")
    private ArrayList<SubscribeContentsDto> subscribeContentsList;
}
