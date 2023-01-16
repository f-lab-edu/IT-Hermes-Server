package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import com.hermes.ithermes.domain.exception.*;
import com.hermes.ithermes.domain.factory.KeywordFactory;
import com.hermes.ithermes.domain.factory.UserFactory;
import com.hermes.ithermes.domain.factory.UserKeywordRegistryFactory;
import com.hermes.ithermes.infrastructure.UserKeywordRegistryRepository;
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
@Transactional(readOnly = true)
public class UserService {

    private final UserKeywordRegistryRepository userKeywordRegistryRepository;
    private final UserFactory userFactory;
    private final KeywordFactory keywordFactory;
    private final UserKeywordRegistryFactory userKeywordRegistryFactory;

    @Transactional
    public CommonResponseDto joinUser(UserCreateUserRequestDto userLoginRequestDto) {
        String loginId = userLoginRequestDto.getId();

        Optional.ofNullable(userLoginRequestDto.getPassword().equals(userLoginRequestDto.getPasswordConfirm()))
                .filter(v -> v)
                .orElseThrow(() -> new UnMatchedPasswordException());

        userFactory.findLoginId(loginId).ifPresent(a -> {
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

        return new CommonResponseDto();
    }

    public CommonResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        userFactory.findLoginIdAndPassword(userLoginRequestDto.getId(), userLoginRequestDto.getPassword()).orElseThrow(() -> new WrongIdOrPasswordException());
        return new CommonResponseDto();
    }

    public CommonResponseDto checkDuplicateNickname(UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        userFactory.findNickname(userDuplicateNicknameRequestDto.getNickname()).ifPresent(a -> {
            throw new SameNicknameException();
        });
        return new CommonResponseDto();
    }

    public CommonResponseDto checkDuplicateId(UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        userFactory.findLoginId(userDuplicateIdRequestDto.getId()).ifPresent(a -> {
            throw new SameIdException();
        });
        return new CommonResponseDto();
    }

    @Transactional
    public CommonResponseDto updateNickname(UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        String newNickname = userUpdateNicknameRequestDto.getNickname();
        userFactory.findNickname(newNickname).ifPresent(a -> {
            throw new SameNicknameException();
        });

        User user = userFactory.findLoginId(userUpdateNicknameRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        user.changeNickname(newNickname);
        return new CommonResponseDto();
    }

    @Transactional
    public CommonResponseDto deleteUser(UserDeleteUserRequestDto userDeleteUserRequestDto) {
        User user = userFactory.findLoginId(userDeleteUserRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());

        user.isDelete();
        return new CommonResponseDto();
    }

    public UserFindMyDataResponseDto findMyData(UserFindMyDataRequestDto userFindMyDataRequestDto) {
        User user = userFactory.findLoginId(userFindMyDataRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        return new UserFindMyDataResponseDto(user.getLoginId(), user.getNickname());
    }
}
