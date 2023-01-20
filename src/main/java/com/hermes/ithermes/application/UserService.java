
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
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserKeywordRegistryRepository userKeywordRegistryRepository;
    private final UserFactory userFactory;
    private final KeywordFactory keywordFactory;
    private final UserRepository userRepository;
    private final UserKeywordRegistryFactory userKeywordRegistryFactory;

    @Transactional
    public CommonResponseDto joinUser(UserCreateUserRequestDto userLoginRequestDto) {
        String loginId = userLoginRequestDto.getId();
        String password = userLoginRequestDto.getPassword();

        Optional.ofNullable(password.equals(userLoginRequestDto.getPasswordConfirm()))
                .filter(v -> v)
                .orElseThrow(() -> new UnMatchedPasswordException());

        if (userFactory.existsByLoginId(loginId)) throw new SameIdException();

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
        String loginId = userLoginRequestDto.getId();
        String password = userLoginRequestDto.getPassword();
        if (!userFactory.existsByLoginIdAndPasswordAndIsDelete(loginId, password)) throw new WrongIdOrPasswordException();
        return new CommonResponseDto();
    }

    public CommonResponseDto checkDuplicateNickname(UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        String nickname = userDuplicateNicknameRequestDto.getNickname();
        if (userRepository.existsByNickname(nickname)) throw new SameNicknameException();
        return new CommonResponseDto();
    }

    public CommonResponseDto checkDuplicateId(UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        String loginId = userDuplicateIdRequestDto.getId();
        if (userFactory.existsByLoginId(loginId)) throw new SameIdException();
        return new CommonResponseDto();
    }

    @Transactional
    public CommonResponseDto updateNickname(UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        String newNickname = userUpdateNicknameRequestDto.getNickname();
        if (userFactory.existsByNickname(newNickname)) throw new SameNicknameException();

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

    public CommonResponseDto updateTelegramId(String telegramKey) {
        TelegramBot bot = new TelegramBot(telegramKey);
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for (Update update : updates) {
                    String chatId = update.message().chat().id().toString();
                    if (update.message().text().equals("/start")) {
                        bot.execute(new SendMessage(chatId, "IT-Hermes에서 사용하는 닉네임을 입력해주세요."));
                    } else {
                        String nickname = update.message().text();
                        if (userRepository.existsUserByNickname(nickname) == false) {
                            bot.execute(new SendMessage(chatId, "먼저 회원가입을 진행해주세요."));
                        } else {
                            if (userRepository.existsUserByNicknameAndTelegramId(chatId, nickname) == true) {
                                bot.execute(new SendMessage(chatId, "이미 생성한 봇이 있는 유저입니다."));
                            } else {
                                userRepository.updateTelegramIdByNickname(chatId, nickname);
                                bot.execute(new SendMessage(chatId, "유저로 등록되었습니다."));
                            }
                        }
                    }
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
        return new CommonResponseDto();
    }
}