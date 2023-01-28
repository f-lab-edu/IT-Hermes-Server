package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmDtoInterface;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final ExternalAlarmClient externalAlarmClient;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;

    public CommonResponseDto sendSubscribeAlarm() {
        List<Long> userIdArr = userRepository.findByTelegramIdIsNotNull().stream()
                .map(m -> m.getId())
                .collect(Collectors.toList());
        for (int i = 0; i < userIdArr.size(); i++) {
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(userIdArr.get(i),CategoryType.YOUTUBE), userIdArr.get(i));
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(userIdArr.get(i),CategoryType.NEWS), userIdArr.get(i));
            externalAlarmClient.sendJobMessage(getUserJobAlarmContents(userIdArr.get(i)), userIdArr.get(i));
        }
        return new CommonResponseDto();
    }

    public CommonResponseDto sendRecommendAlarm(){
        List<Long> userIdArr = userRepository.findByTelegramIdIsNotNull().stream()
                .map(m -> m.getId())
                .collect(Collectors.toList());
        for(int i = 0; i < userIdArr.size(); i++){
            externalAlarmClient.sendContentsMessage(getUserRecommendAlarmContents(userIdArr.get(i),CategoryType.YOUTUBE), userIdArr.get(i));
            externalAlarmClient.sendContentsMessage(getUserRecommendAlarmContents(userIdArr.get(i),CategoryType.NEWS), userIdArr.get(i));
        }
        return new CommonResponseDto();
    }

    public List<AlarmDtoInterface> getUserYoutubeAndNewsAlarmContents(long userIdx, CategoryType type){
        List<Subscribe> subscribe = subscribeRepository.findByUserIdAndCategoryAndIsActive(userIdx,type,ActiveType.ACTIVE);
        List<YoutubeAndNews> youtubeAndNewsAlarmList = new ArrayList<>();
        Long startIdx = Long.valueOf(0);
        for(int i = 0; i < subscribe.size(); i++){
            List<YoutubeAndNews> youtubeAndNewsList = youtubeAndNewsRepository.findYoutubeAndNewsByContentsProvider(subscribe.get(i).getContentsProvider());
            if(subscribe.get(i).getAlarmLastUrl()!=null){
                startIdx = youtubeAndNewsRepository.findYoutubeAndNewsByUrl(subscribe.get(i).getAlarmLastUrl()).getId()+1;
            }
            for(long j = startIdx; j < youtubeAndNewsList.size(); j++) {
                youtubeAndNewsAlarmList.add(youtubeAndNewsList.get((int) j));
            }
            String lastUrl = youtubeAndNewsAlarmList.get(youtubeAndNewsAlarmList.size()-1).getUrl();
            Subscribe subscribe1 = subscribeRepository.findByUserIdAndContentsProvider(userIdx,subscribe.get(i).getContentsProvider());
            subscribe1.setAlarmLastUrl(lastUrl);
            subscribeRepository.save(subscribe1);
        }
        return youtubeAndNewsAlarmList.stream()
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<JobAlarmDto> getUserJobAlarmContents(long userIdx){
        List<Subscribe> subscribe = subscribeRepository.findByUserIdAndCategoryAndIsActive(userIdx,CategoryType.JOB,ActiveType.ACTIVE);
        List<Job> jobAlarmList = new ArrayList<>();
        Long startIdx = Long.valueOf(0);
        for(int i = 0; i < subscribe.size(); i++){
            List<Job> jobList = jobRepository.findJobByContentsProvider(subscribe.get(i).getContentsProvider());
            if(subscribe.get(i).getAlarmLastUrl()!=null){
                startIdx = jobRepository.findJobByUrl(subscribe.get(i).getAlarmLastUrl()).getId()+1;
            }
            for(long j = startIdx; j < jobList.size(); j++){
                jobAlarmList.add(jobAlarmList.get((int) j));
            }
            String lastUrl = jobAlarmList.get(jobAlarmList.size()-1).getUrl();
            Subscribe subscribe1 = subscribeRepository.findByUserIdAndContentsProvider(userIdx,subscribe.get(i).getContentsProvider());
            subscribe1.setAlarmLastUrl(lastUrl);
            subscribeRepository.save(subscribe1);
        }
        return jobAlarmList.stream()
                .map(m -> JobAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<AlarmDtoInterface> getUserRecommendAlarmContents(long userIdx, CategoryType type){
        List<String> userRecommendKeywords = getRecommendKeywords(userIdx,type);
        List<ContentsProviderType> userContentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx, type);
        return userContentsProviderList.stream()
                        .map(m -> youtubeAndNewsRepository.findYoutubeAndNewsByContentsProvider(m))
                        .flatMap(List::stream)
                        .filter(m -> userRecommendKeywords.contains(m.getTitle()))
                        .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                        .collect(Collectors.toList());
    }

    public List<String> getRecommendKeywords(long userIdx,CategoryType type){
        List<String> keywords = new ArrayList<>();
        keywords.add("머신러닝");
        keywords.add("빅데이터");
        keywords.add("보안");
        keywords.add("오픈소스");
        keywords.add("클라우드");
        keywords.add("프레임워크");
        /*
        List<String> userCustomKeywords = userRepository.findUsersById(userIdx).getJob();
        userCustomKeywords.stream()
                .map(m -> keywords.add(m));*/
        return keywords;
    }

}
