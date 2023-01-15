package com.hermes.ithermes.presentation.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmFindAlarmResponseDto {
    /** 서비스 구독 활성화 정보 */
    private List<String> keywordList;
    /** 취업 옵션 데이터 정보, 데이터가 없을 시, 배열에 null 삽입*/
    private String job;
    /** 취업 공고 시작 및 종료 옵션 데이터 정보, 데이터가 없을 시, 배열에 null 삽입*/
    private String minYearOfExperience;
    private String maxYearOfExperience;
}
