package com.hermes.ithermes.presentation.dto.user;

import com.hermes.ithermes.presentation.dto.subscribe.SubscribeContentsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserFindUserListResponseDto {
    String loginId;
    String nickname;
    String jobType;
    int yearOfExperience;
    List<SubscribeContentsDto> subscribeList;
}
