package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.*;
import com.hermes.ithermes.infrastructure.*;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmDtoInterface;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

    private final ExternalAlarmClient externalAlarmClient;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;
    private final CrawlingContentsLastUrlRepository crawlingContentsLastUrlRepository;

    public CommonResponseDto sendSubscribeAlarm() {
        List<Subscribe> youtubeAlarm = subscribeRepository.findAlarmJoin(ActiveType.ACTIVE,CategoryType.YOUTUBE);
        List<Subscribe> newsAlarm = subscribeRepository.findAlarmJoin(ActiveType.ACTIVE,CategoryType.NEWS);
        List<Subscribe> jobAlarm = subscribeRepository.findAlarmJoin(ActiveType.ACTIVE,CategoryType.JOB);

        for(int i = 0; i< youtubeAlarm.size(); i++){
            User alarmUser = youtubeAlarm.get(i).getUser();
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(youtubeAlarm.get(i)),alarmUser.getTelegramId());
        }

        for(int i = 0; i < newsAlarm.size(); i++){
            User alarmUser = newsAlarm.get(i).getUser();
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(newsAlarm.get(i)),alarmUser.getTelegramId());
        }

        /*
        for(int i = 0; i < jobAlarm.size(); i++){
            User alarmUser = jobAlarm.get(i).getUser();
            externalAlarmClient.sendJobMessage(getUserJobAlarmContents(jobAlarm.get(i)),alarmUser.getTelegramId());
        }*/

        return new CommonResponseDto();
    }

    public CommonResponseDto sendRecommendAlarm(){
        List<Long> userIdArr = userRepository.findUserId();

        for(int i = 0; i < userIdArr.size(); i++){
            //externalAlarmClient.sendContentsMessage(getUserRecommendAlarmContents(userIdArr.get(i),CategoryType.YOUTUBE), userIdArr.get(i));
           // externalAlarmClient.sendContentsMessage(getUserRecommendAlarmContents(userIdArr.get(i),CategoryType.NEWS), userIdArr.get(i));
            //externalAlarmClient.sendJobMessage(getUserRecommendAlarmJobContents(userIdArr.get(i)),userIdArr.get(i));
        }

        return new CommonResponseDto();
    }


    public List<AlarmDtoInterface> getUserYoutubeAndNewsAlarmContents(Subscribe youtubeAndNewsSubscribe) {
        List<YoutubeAndNews> youtubeAndNewsAlarmList = new ArrayList<>();

        youtubeAndNewsAlarmList = youtubeAndNewsRepository.findYoutubeAndNewsByUrlGreater(crawlingContentsLastUrlRepository.findByContentsProvider(youtubeAndNewsSubscribe.getContentsProvider()).get().getLastUrl(),youtubeAndNewsSubscribe.getContentsProvider());

        return youtubeAndNewsAlarmList.stream()
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<JobAlarmDto> getUserJobAlarmContents(Subscribe jobSubscribe){
        List<Job> jobAlarmList = new ArrayList<>();

        jobAlarmList = jobRepository.findJobByUrlGreater(crawlingContentsLastUrlRepository.findByContentsProvider(jobSubscribe.getContentsProvider()).get().getLastUrl(),jobSubscribe.getContentsProvider(),GradeType.checkGradleType(jobSubscribe.getUser().getYearOfExperience()),jobSubscribe.getUser().getJob());

        return jobAlarmList.stream()
                .map(m -> JobAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<AlarmDtoInterface> getUserRecommendAlarmContents(long userIdx, CategoryType type){
        List<String> userRecommendKeywords = getRecommendKeywords(userIdx);
        List<ContentsProviderType> userContentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx, type);

        List<YoutubeAndNews> youtubeAndNewsSubscribeContents = userContentsProviderList.stream()
                        .map(m -> youtubeAndNewsRepository.findYoutubeAndNewsByContentsProvider(m))
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

        List<YoutubeAndNews> youtubeAndNewsRecommendList = youtubeAndNewsSubscribeContents.stream()
                .filter(m -> m.isContainRecommendKeywords(userRecommendKeywords))
                .collect(Collectors.toList());

        return youtubeAndNewsRecommendList.stream()
                .distinct()
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<JobAlarmDto> getUserRecommendAlarmJobContents(long userIdx){
        List<String> userRecommendKeywords = getRecommendKeywords(userIdx);
        List<ContentsProviderType> userContentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx, CategoryType.JOB);

        List<Job> jobSubscribeContents = userContentsProviderList.stream()
                .map(m -> jobRepository.findJobByContentsProvider(m))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<Job> jobRecommendList = jobSubscribeContents.stream()
                .filter(m -> m.isContainRecommendKeywords(userRecommendKeywords))
                .collect(Collectors.toList());

        return jobRecommendList.stream()
                .distinct()
                .map(m -> JobAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<String> getRecommendKeywords(long userIdx){
        List<String> userCustomKeywords = userRepository.findUsersById(userIdx).getJob().getKeywords();

        Stream.of(RecommendKeywordType.values())
                .forEach(m -> userCustomKeywords.add(m.getName()));

        return userCustomKeywords;
    }

}
