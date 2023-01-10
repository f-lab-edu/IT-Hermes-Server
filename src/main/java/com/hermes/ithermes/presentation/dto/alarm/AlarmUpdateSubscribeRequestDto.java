package com.hermes.ithermes.presentation.dto.alarm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AlarmUpdateSubscribeRequestDto {
    @NotBlank(message = "아이디는 필수 입력사항 입니다.")
    private String id;

    /** 서비스 구독 활성화 정보, 비활성화 시, 배열에 "0" 삽입*/
    @NotEmpty(message = "서비스정보는 필수 입력사항 입니다.")
    private String[] keywordList;
    /** 취업 옵션 데이터 정보, 데이터가 없을 시, 배열에 null 삽입*/
    @NotEmpty(message = "취업 옵션 정보는 필수 입력사항 입니다.")
    private String job;
    /** 취업 공고 시작 및 종료 옵션 데이터 정보, 데이터가 없을 시, 배열에 null 삽입*/
    @NotEmpty(message = "취업 공고 시작 옵션 정보는 필수 입력사항 입니다.")
    private String startDateOfExperience;
    @NotEmpty(message = "취업 공고 종료 옵션 정보는 필수 입력사항 입니다.")
    private String endDateOfExperience;
}
