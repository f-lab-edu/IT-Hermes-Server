package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class UserFactory {

    public User parseLoginRequestDtoToUser(UserCreateUserRequestDto userLoginRequestDto) {

        User user = User.builder()
                .loginId(userLoginRequestDto.getId())
                .password(userLoginRequestDto.getPassword())
                .nickname(userLoginRequestDto.getNickname())
                .job(JobType.parseJobType("backend"))
                .yearOfExperience(Integer.parseInt(userLoginRequestDto.getExperience()))
                .build();
        user.initDefaultValue();
        return user;
    }
}