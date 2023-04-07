package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

@RequiredArgsConstructor
public class UpdateUserChatId {

    private final RabbitTemplate rabbitTemplate;

    public void updateUserChatId(UserRepository userRepository){

        telegramBot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for(Update update : updates){
                    String chatId = update.message().chat().id().toString();
                    String userSendMessage = update.message().text();

                    if(userSendMessage.equals("/start")){
                        telegramBot.execute(new SendMessage(chatId, "IT-Hermes에서 사용하는 닉네임을 입력해주세요."));
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }

                    if(userRepository.existsUserByNickname(userSendMessage) == false){
                        telegramBot.execute(new SendMessage(chatId,"존재하지 않는 유저입니다. 먼저 회원가입을 진행해주세요."));
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }

                    if(userRepository.existsUserByNicknameAndTelegramId(chatId,userSendMessage) == true){
                        telegramBot.execute(new SendMessage(chatId,"이미 생성한 봇이 있는 유저입니다."));
                    }else{
                        User newUser = userRepository.findByNickname(userSendMessage);
                        newUser.updateTelegramId(chatId);
                        userRepository.save(newUser);
                        telegramBot.execute(new SendMessage(chatId,"유저로 등록되었습니다."));
                    }

                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }

        });
    }
}
