package com.hermes.ithermes.presentation.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class UserFindMyDataRequestDto {
    @NonNull
    private String id;
}
