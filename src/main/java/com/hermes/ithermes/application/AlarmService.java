package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private YoutubeAndNewsRepository youtubeAndNewsRepository;

    public void alarm(String telegramKey){
        List<String> keywords=new ArrayList<>();
        keywords.add("오픈소스");
        keywords.add("머신러닝");
        keywords.add("빅데이터");
        keywords.add("보안");
        keywords.add("클라우드");

        TelegramBot bot=new TelegramBot(telegramKey);
        List<Long> userIdArr=userRepository.findByTelegramIdIsNotNull().stream()
                .map(m->m.getId())
                .collect(Collectors.toList());

        for(int i=0; i<userIdArr.size(); i++){
            List<YoutubeAndNewsAlarmDto> youtubeAndNewsUserAlarmList=youtubeAndNewsRepository.getYoutubeAndNewsAlarm((long) i);
            String alarmMessage="";
            for(int j=0; j<youtubeAndNewsUserAlarmList.size(); j++){
                alarmMessage+=youtubeAndNewsUserAlarmList.get(j).getTitle();
                alarmMessage+=youtubeAndNewsUserAlarmList.get(j).getName();
                alarmMessage+=youtubeAndNewsUserAlarmList.get(j).getImage();
                alarmMessage+=youtubeAndNewsUserAlarmList.get(i).getDescription();
                alarmMessage+=youtubeAndNewsUserAlarmList.get(i).getUrl();
                alarmMessage+=youtubeAndNewsUserAlarmList.get(i).getCategoryType().getTitle();
                bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdArr.get(i)),alarmMessage));
            }
        }



    }

}
