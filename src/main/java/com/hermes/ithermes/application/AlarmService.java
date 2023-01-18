package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    @Value("${telegram-key}")
    private String telegramKey;

    private final UserRepository userRepository;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;

    public CommonResponseDto alarm(){
        List<Long> userIdArr=userRepository.findByTelegramIdIsNotNull().stream()
                .map(m->m.getId())
                .collect(Collectors.toList());

        for(int i=0; i<userIdArr.size(); i++){
            sendYoutubeAndNewsMessage(youtubeAndNewsRepository.getYoutubeAndNewsAlarmContents(userIdArr.get(i)), userIdArr.get(i));
            sendJobAlarmMessage(jobRepository.getJobAlarmContents(userIdArr.get(i)),userIdArr.get(i));
        }
        return new CommonResponseDto();
    }

    public void sendYoutubeAndNewsMessage(List<YoutubeAndNewsAlarmDto> youtubeAndNewsAlarmDtoList,long userIdx){
        TelegramBot bot=new TelegramBot(telegramKey);
        for(int j=0; j<youtubeAndNewsAlarmDtoList.size(); j++){
            StringBuilder youtubeAndNewsAlarmMessage=new StringBuilder();
            youtubeAndNewsAlarmMessage.append("[유투브 및 뉴스 정보]")
                    .append("\n")
                    .append("[제목]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getTitle())
                    .append("\n")
                    .append("[본문]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getDescription())
                    .append("\n")
                    .append("[이미지]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getImage())
                    .append("\n")
                    .append("[url]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getUrl())
                    .append("\n")
                    .append("[일자]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getContentsStartAt())
                    .append("\n")
                    .append("[서비스]")
                    .append(youtubeAndNewsAlarmDtoList.get(j).getContentsProviderType().getTitle());
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),youtubeAndNewsAlarmMessage.toString()));
        }
    }

    public void sendJobAlarmMessage(List<JobAlarmDto> jobAlarmDtoList,long userIdx){
        TelegramBot bot=new TelegramBot(telegramKey);
        for(int i=0; i<jobAlarmDtoList.size(); i++){
            StringBuilder jobAlarmMessage=new StringBuilder();
            jobAlarmMessage.append("[채용 정보]")
                    .append("\n")
                    .append("[제목]")
                    .append(jobAlarmDtoList.get(i).getTitle())
                    .append("\n")
                    .append("[회사]")
                    .append(jobAlarmDtoList.get(i).getCompany())
                    .append("\n")
                    .append("[위치]")
                    .append(jobAlarmDtoList.get(i).getLocation())
                    .append("\n")
                    .append("[url]")
                    .append(jobAlarmDtoList.get(i).getUrl())
                    .append("\n")
                    .append("[서비스]")
                    .append(jobAlarmDtoList.get(i).getContentsProviderType().getTitle())
                    .append("\n")
                    .append("[마감일]")
                    .append(jobAlarmDtoList.get(i).getContentsEndAt());
            bot.execute(new SendMessage(userRepository.findTelegramIdByUserId(userIdx),jobAlarmMessage.toString()));
        }
    }

}
