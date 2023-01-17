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
            String youtubeAndNewsAlarmMessage="";
            youtubeAndNewsAlarmMessage+=youtubeAndNewsAlarmDtoList.get(j).getTitle();
            youtubeAndNewsAlarmMessage+="\n";
            youtubeAndNewsAlarmMessage+=youtubeAndNewsAlarmDtoList.get(j).getName();
            youtubeAndNewsAlarmMessage+="\n";
            youtubeAndNewsAlarmMessage+=youtubeAndNewsAlarmDtoList.get(j).getImage();
            youtubeAndNewsAlarmMessage+="\n";
            youtubeAndNewsAlarmMessage+=youtubeAndNewsAlarmDtoList.get(j).getDescription();
            youtubeAndNewsAlarmMessage+="\n";
            youtubeAndNewsAlarmMessage+=youtubeAndNewsAlarmDtoList.get(j).getUrl();
            youtubeAndNewsAlarmMessage+="\n";
            youtubeAndNewsAlarmMessage+=youtubeAndNewsAlarmDtoList.get(j).getCategoryType().getTitle();
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),youtubeAndNewsAlarmMessage));
        }
    }

    public void sendJobAlarmMessage(List<JobAlarmDto> jobAlarmDtoList,long userIdx){
        TelegramBot bot=new TelegramBot(telegramKey);
        for(int i=0; i<jobAlarmDtoList.size(); i++){
            String jobAlarmMessage="";
            jobAlarmMessage+=jobAlarmDtoList.get(i).getTitle();
            jobAlarmMessage+="\n";
            jobAlarmMessage+=jobAlarmDtoList.get(i).getCompany();
            jobAlarmMessage+="\n";
            jobAlarmMessage+=jobAlarmDtoList.get(i).getCategoryType().getTitle();
            jobAlarmMessage+="\n";
            jobAlarmMessage+=jobAlarmDtoList.get(i).getLocation();
            jobAlarmMessage+="\n";
            jobAlarmMessage+=jobAlarmDtoList.get(i).getUrl();
            jobAlarmMessage+="\n";
            jobAlarmMessage+=jobAlarmDtoList.get(i).getContentsEndAt();
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),jobAlarmMessage));
        }
    }




}
