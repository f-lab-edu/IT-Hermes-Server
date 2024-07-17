package com.hermes.ithermes.presentation.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeFindSubscribeResponseDto {
    /** 서비스 구독 활성화 정보 */
    private List<String> keywordList;
}
