package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    @Value("${telegram-key}")
    private String telegramKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private YoutubeAndNewsRepository youtubeAndNewsRepository;

    @Autowired
    private JobRepository jobRepository;

    public void alarm(String telegramKey){
        List<Long> userIdArr=userRepository.findByTelegramIdIsNotNull().stream()
                .map(m->m.getId())
                .collect(Collectors.toList());

        for(int i=0; i<userIdArr.size(); i++){
            List<YoutubeAndNewsAlarmDto> youtubeAndNewsUserAlarmList=youtubeAndNewsRepository.getYoutubeAndNewsAlarm((long) i);
            List<JobAlarmDto> jobAlarmDtoList=jobRepository.getJobAlarm((long)i);
            sendYoutubeAndNewsMessage(youtubeAndNewsUserAlarmList,i);
            sendJobAlarmMessage(jobAlarmDtoList,i);
        }
    }

    public void sendYoutubeAndNewsMessage(List<YoutubeAndNewsAlarmDto> youtubeAndNewsAlarmDtoList,long userIdx){
        TelegramBot bot=new TelegramBot(telegramKey);
        for(int j=0; j<youtubeAndNewsAlarmDtoList.size(); j++){
            StringBuilder youtubeAndNewsAlarmMessage=new StringBuilder();
            youtubeAndNewsAlarmMessage.append("[유투브 및 뉴스 정보]")
                    .append("\n")
                    .append("[title]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getTitle())
                    .append("\n")
                    .append("[name]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getName())
                    .append("\n")
                    .append("[image]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getImage())
                    .append("\n")
                    .append("[description]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getDescription())
                    .append("\n")
                    .append("[url]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getUrl())
                    .append("\n")
                    .append("[category]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getCategoryType().getTitle());
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),youtubeAndNewsAlarmMessage.toString()));
        }
    }

    public void sendJobAlarmMessage(List<JobAlarmDto> jobAlarmDtoList,long userIdx){
        TelegramBot bot=new TelegramBot(telegramKey);
        for(int i=0; i<jobAlarmDtoList.size(); i++){
            StringBuilder jobAlarmMessage=new StringBuilder();
            jobAlarmMessage.append("[채용 정보]")
                    .append("\n")
                    .append("[title]")
                    .append(jobAlarmDtoList.get(i).getTitle())
                    .append("\n")
                    .append("[company]")
                    .append(jobAlarmDtoList.get(i).getCompany())
                    .append("\n")
                    .append("[category]")
                    .append(jobAlarmDtoList.get(i).getCategoryType().getTitle())
                    .append("\n")
                    .append("[location]")
                    .append(jobAlarmDtoList.get(i).getLocation())
                    .append("\n")
                    .append("[url]")
                    .append(jobAlarmDtoList.get(i).getUrl())
                    .append("\n")
                    .append("[마감일]")
                    .append(jobAlarmDtoList.get(i).getContentsEndAt());
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),jobAlarmMessage.toString()));
        }
    }




}
