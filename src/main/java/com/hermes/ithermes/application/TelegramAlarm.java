package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.*;
import com.hermes.ithermes.presentation.dto.alarm.AlarmDtoInterface;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramAlarm implements ExternalAlarmClient {
    private final TelegramBot telegramBot;

    private final UserRepository userRepository;

    @Override
    public void sendContentsMessage(List<AlarmDtoInterface> contentsAlarmDtoList, String telegramId) {

        for(int i = 0; i < contentsAlarmDtoList.size(); i++){
            StringBuilder youtubeAlarmMessage = new StringBuilder();

            youtubeAlarmMessage.append("[유투브 및 뉴스 정보]" + "\n")
                    .append("[제목]" + contentsAlarmDtoList.get(i).title() + "\n")
                    .append("[본문]" + contentsAlarmDtoList.get(i).description() + "\n")
                    .append("[이미지]" + contentsAlarmDtoList.get(i).image() + "\n")
                    .append("[url]" + contentsAlarmDtoList.get(i).url() + "\n")
                    .append("[일자]" + contentsAlarmDtoList.get(i).contentsStartAt() + "\n")
                    .append("[서비스]" + contentsAlarmDtoList.get(i).contentsProvider() + "\n");

            telegramBot.execute(new SendMessage(telegramId,youtubeAlarmMessage.toString()));
        }

    }

    @Override
    public void sendJobMessage(List<JobAlarmDto> jobAlarmDtoList, String telegramId) {

        for(int i = 0;  i < jobAlarmDtoList.size(); i++) {
            StringBuilder jobAlarmMessage = new StringBuilder();

            jobAlarmMessage.append("[채용 정보]" + "\n")
                    .append("[제목]" + jobAlarmDtoList.get(i).getTitle() + "\n")
                    .append("[회사]" + jobAlarmDtoList.get(i).getCompany() + "\n")
                    .append("[위치]" + jobAlarmDtoList.get(i).getLocation() + "\n")
                    .append("[url]" + jobAlarmDtoList.get(i).getUrl() + "\n")
                    .append("[서비스]" + jobAlarmDtoList.get(i).getContentsProviderType() + "\n")
                    .append("[마감일]" + jobAlarmDtoList.get(i).getContentsEndAt());

            telegramBot.execute(new SendMessage(telegramId,jobAlarmMessage.toString()));
        }

    }
}
