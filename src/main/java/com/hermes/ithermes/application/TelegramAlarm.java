package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.*;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramAlarm implements ExternalAlarmClient {

    @Value("${telegram-key}")
    private String telegramKey;
    private TelegramBot bot=new TelegramBot(telegramKey);

    private final UserRepository userRepository;

    @Override
    public void sendYoutubeMessage(List<YoutubeAndNewsAlarmDto> youtubeAlarmDtoList, long userIdx) {
        for(int i = 0; i < youtubeAlarmDtoList.size(); i++){
            StringBuilder youtubeAlarmMessage = new StringBuilder();
            youtubeAlarmMessage.append("[유투브 정보] + \n")
                    .append("[제목]" + youtubeAlarmDtoList.get(i).getTitle() + "\n")
                    .append("[본문]" + youtubeAlarmDtoList.get(i).getDescription() + "\n")
                    .append("[이미지]" + youtubeAlarmDtoList.get(i).getImage() + "\n")
                    .append("[url]" + youtubeAlarmDtoList.get(i).getUrl() + "\n")
                    .append("[일자]" + youtubeAlarmDtoList.get(i).getContentsStartAt() + "\n")
                    .append("[서비스]" + youtubeAlarmDtoList.get(i).getContentsProviderType().getTitle() + "\n");
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),youtubeAlarmMessage.toString()));
        }
    }

    @Override
    public void sendNewsMessage(List<YoutubeAndNewsAlarmDto> newsAlarmDtoList, long userIdx) {
        for(int i = 0; i < newsAlarmDtoList.size(); i++){
            StringBuilder newsAlarmMessage = new StringBuilder();
            newsAlarmMessage.append("[뉴스 정보] + \n")
                    .append("[제목]" + newsAlarmDtoList.get(i).getTitle() + "\n")
                    .append("[본문]" + newsAlarmDtoList.get(i).getDescription() + "\n")
                    .append("[이미지]" + newsAlarmDtoList.get(i).getImage() + "\n")
                    .append("[url]" + newsAlarmDtoList.get(i).getUrl() + "\n")
                    .append("[일자]" + newsAlarmDtoList.get(i).getContentsStartAt() + "\n")
                    .append("[서비스]" + newsAlarmDtoList.get(i).getContentsProviderType().getTitle() + "\n");
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),newsAlarmMessage.toString()));
        }
    }

    @Override
    public void sendJobMessage(List<JobAlarmDto> jobAlarmDtoList, long userIdx) {
        for(int i = 0;  i < jobAlarmDtoList.size(); i++) {
            StringBuilder jobAlarmMessage = new StringBuilder();
            jobAlarmMessage.append("[채용 정보]" + "\n")
                    .append("[제목]" + jobAlarmDtoList.get(i).getTitle() + "\n")
                    .append("[회사]" + jobAlarmDtoList.get(i).getCompany() + "\n")
                    .append("[위치]" + jobAlarmDtoList.get(i).getLocation() + "\n")
                    .append("[url]" + jobAlarmDtoList.get(i).getUrl() + "\n")
                    .append("[서비스]" + jobAlarmDtoList.get(i).getContentsProviderType().getTitle() + "\n")
                    .append("[마감일]" + jobAlarmDtoList.get(i).getContentsEndAt());
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),jobAlarmMessage.toString()));
        }
    }
}
