package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Builder
@Component
@RequiredArgsConstructor
public class UserFactory {
    private final UserRepository userRepository;

    public User parseLoginRequestDtoToUser(UserCreateUserRequestDto userLoginRequestDto) {
        User user = User.builder()
                .loginId(userLoginRequestDto.getId())
                .password(userLoginRequestDto.getPassword())
                .nickname(userLoginRequestDto.getNickname())
                .job(userLoginRequestDto.getJob())
                .yearOfExperience(Integer.parseInt(userLoginRequestDto.getYearOfExperience()))
                .isDelete(false)
                .build();
        return user;
    }

    public Optional<User> findLoginId(String userId) {
        return userRepository.findByLoginId(userId);
    }

    public boolean existsByLoginId(String userId) {
        return userRepository.existsByLoginId(userId);
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean existsByLoginIdAndPassword(String id, String password) {
        return userRepository.existsByLoginIdAndPassword(id, password);
    }
}
