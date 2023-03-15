package com.hermes.ithermes.presentation.dto.alarm;

import com.hermes.ithermes.domain.util.ContentsProviderType;

import java.time.LocalDateTime;

public interface AlarmDtoInterface {

    String title();
    String description();
    String image();
    String url();
    LocalDateTime contentsStartAt();
    ContentsProviderType contentsProvider();
}
