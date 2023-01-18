package com.hermes.ithermes.presentation.dto.alarm;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class JobAlarmDto {

    private String title;

    private String location;

    private String company;

    private String url;

    private LocalDateTime contentsEndAt;

    private ContentsProviderType contentsProviderType;

}
