package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.UserRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@RequiredArgsConstructor
public class UpdateUserChatId {

    @Value("${telegram-key}")
    private String telegramKey;

    private UserRepository userRepository;

    TelegramBot bot=new TelegramBot(telegramKey);

    public void updateUserChatId(){
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for(Update update : updates){
                    String chatId = update.message().chat().id().toString();

                    if (update.message().text().equals("/start")) {
                        bot.execute(new SendMessage(chatId,"IT-Hermes에서 사용하는 닉네임을 입력해주세요."));
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }
                    String nickname = update.message().text();
                    if (userRepository.existsUserByNickname(nickname) == false) {
                        bot.execute(new SendMessage(chatId,"먼저 회원가입을 진행해주세요."));
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }
                    if (userRepository.existsUserByNicknameAndTelegramId(chatId,nickname) == true) {
                        bot.execute(new SendMessage(chatId,"이미 생성한 봇이 있는 유저입니다."));
                    } else {
                        userRepository.save(userRepository.findByNickname(nickname).get());
                        bot.execute(new SendMessage(chatId,"유저로 등록되었습니다."));
                    }
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

}
