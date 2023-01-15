package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import com.hermes.ithermes.domain.exception.*;
import com.hermes.ithermes.domain.factory.KeywordFactory;
import com.hermes.ithermes.domain.factory.UserFactory;
import com.hermes.ithermes.domain.factory.UserKeywordRegistryFactory;
import com.hermes.ithermes.infrastructure.UserKeywordRegistryRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserKeywordRegistryRepository userKeywordRegistryRepository;
    private final UserFactory userFactory;
    private final KeywordFactory keywordFactory;
    private final UserKeywordRegistryFactory userKeywordRegistryFactory;

    @Transactional
    public UserCreateUserResponseDto joinUser(UserCreateUserRequestDto userLoginRequestDto) {

        Optional.ofNullable(userLoginRequestDto.getPassword().equals(userLoginRequestDto.getPasswordConfirm()))
                .filter(v -> v)
                .orElseThrow(() -> new UnMatchedPasswordException());

        findLoginId(userLoginRequestDto.getId()).ifPresent(a -> {
            throw new SameUserException();
        });
        User user = userFactory.parseLoginRequestDtoToUser(userLoginRequestDto);

        Arrays.stream(userLoginRequestDto.getKeywordList())
                .filter(v -> Objects.nonNull(v))
                .forEach(v -> {
                    Keyword keyword = keywordFactory.parseKeywordNameToKeyword(v);
                    UserKeywordRegistry userKeywordRegistry = userKeywordRegistryFactory.parseUserAndKeyword(user, keyword);
                    userKeywordRegistryRepository.save(userKeywordRegistry);
                });

        return new UserCreateUserResponseDto("success");
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        findLoginIdAndPassword(userLoginRequestDto.getId(), userLoginRequestDto.getPassword()).orElseThrow(() -> new WrongIdOrPasswordException());
        return new UserLoginResponseDto("success");
    }

    @Transactional(readOnly = true)
    public UserDuplicateNicknameResponseDto checkDuplicateNickname(UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        findNickname(userDuplicateNicknameRequestDto.getNickname()).ifPresent(a -> {
            throw new SameNicknameException();
        });
        return new UserDuplicateNicknameResponseDto("success");
    }

    @Transactional(readOnly = true)
    public UserDuplicateIdResponseDto checkDuplicateId(UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        findLoginId(userDuplicateIdRequestDto.getId()).ifPresent(a -> {
            throw new SameIdException();
        });
        return new UserDuplicateIdResponseDto("success");
    }

    @Transactional
    public UserUpdateNicknameResponseDto updateNickname(UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        String newNickname = userUpdateNicknameRequestDto.getNickname();
        findNickname(newNickname).ifPresent(a -> {
            throw new SameNicknameException();
        });

        User user = findLoginId(userUpdateNicknameRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        user.changeNickname(newNickname);
        return new UserUpdateNicknameResponseDto("success");
    }

    @Transactional
    public CommonResponseDto deleteUser(UserDeleteUserRequestDto userDeleteUserRequestDto) {
        User user = findLoginId(userDeleteUserRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());

        user.isDelete();
        return new CommonResponseDto();
    }

    @Transactional(readOnly = true)
    public UserFindMyDataResponseDto findMyData(UserFindMyDataRequestDto userFindMyDataRequestDto) {
        User user = findLoginId(userFindMyDataRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        return new UserFindMyDataResponseDto(user.getLoginId(), user.getNickname());
    }

    private Optional<User> findLoginId(String userId) {
        return userRepository.findByLoginId(userId);
    }

    private Optional<User> findNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    private Optional<User> findLoginIdAndPassword(String id, String password) {
        return userRepository.findByLoginIdAndPasswordAndIsDelete(id, password, false);
    }
}
