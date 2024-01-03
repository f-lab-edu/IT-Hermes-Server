package com.hermes.ithermes.presentation.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeContentsDto {
    private String contentsProvider;
    private String isActive;
}
