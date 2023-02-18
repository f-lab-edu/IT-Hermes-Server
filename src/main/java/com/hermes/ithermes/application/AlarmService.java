package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.RecommendKeywordType;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
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
            externalAlarmClient.sendJobMessage(getUserRecommendAlarmJobContents(userIdArr.get(i)),userIdArr.get(i));
        }

        return new CommonResponseDto();
    }

    public List<AlarmDtoInterface> getUserYoutubeAndNewsAlarmContents(long userIdx, CategoryType type){
        List<Subscribe> subscribe = subscribeRepository.findByUserIdAndCategoryAndIsActive(userIdx,type,ActiveType.ACTIVE);
        List<YoutubeAndNews> youtubeAndNewsAlarmList = new ArrayList<>();

        for(int i = 0; i < subscribe.size(); i++){
            youtubeAndNewsAlarmList = youtubeAndNewsRepository.findByUrlGreaterThanAndContentsProvider(subscribe.get(i).getAlarmLastUrl(),subscribe.get(i).getContentsProvider());

            if(youtubeAndNewsAlarmList.size()>0){
                updateUserSubscribeContentsLastUrl(youtubeAndNewsAlarmList.get(youtubeAndNewsAlarmList.size()-1).getUrl(),userIdx,subscribe.get(i).getContentsProvider());
            }
        }

        return youtubeAndNewsAlarmList.stream()
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<JobAlarmDto> getUserJobAlarmContents(long userIdx){
        List<Subscribe> subscribe = subscribeRepository.findByUserIdAndCategoryAndIsActive(userIdx,CategoryType.JOB,ActiveType.ACTIVE);
        List<Job> jobAlarmList = new ArrayList<>();

        for(int i = 0; i < subscribe.size(); i++){
            jobAlarmList = jobRepository.findJobByUrlGreaterThanAndContentsProvider(subscribe.get(i).getAlarmLastUrl(),subscribe.get(i).getContentsProvider());

            if(jobAlarmList.size()>0){
                updateUserSubscribeContentsLastUrl(jobAlarmList.get(jobAlarmList.size()-1).getUrl(),userIdx,subscribe.get(i).getContentsProvider());
            }
        }

        return jobAlarmList.stream()
                .map(m -> JobAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public void updateUserSubscribeContentsLastUrl(String lastUrl,long userIdx,ContentsProviderType contentsProvider){
        Subscribe subscribe = subscribeRepository.findByUserIdAndContentsProvider(userIdx,contentsProvider);
        subscribe.setAlarmLastUrl(lastUrl);
        subscribeRepository.save(subscribe);
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
