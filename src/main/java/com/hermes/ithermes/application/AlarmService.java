package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.*;
import com.hermes.ithermes.domain.exception.NoAlarmDataException;
import com.hermes.ithermes.domain.util.*;
import com.hermes.ithermes.infrastructure.*;
import com.hermes.ithermes.infrastructure.elastic.AlarmSearchRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmDtoInterface;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle.CrawlingContentsLastUrlFindAllResponseDto;
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
    private final CrawlingContentsLastUrlRepository crawlingContentsLastUrlRepository;
    private final AlarmSearchRepository alarmSearchRepository;

    public CommonResponseDto sendSubscribeAlarm() {
        List<AlarmSearch> youtubeAlarm = alarmSearchRepository.findByCategory(CategoryType.YOUTUBE)
                .orElseThrow(()-> new NoAlarmDataException());
        /*List<AlarmSearch> newsAlarm = alarmSearchRepository.findByCategory(CategoryType.NEWS)
                .orElseThrow(()-> new NoAlarmDataException());*/
       /*List<AlarmSearch> jobAlarm = alarmSearchRepository.findByCategory(CategoryType.JOB)
                .orElseThrow(()-> new NoAlarmDataException());*/

        for(int i = 0; i< youtubeAlarm.size(); i++){
            AlarmSearch alarmSearch = youtubeAlarm.get(i);
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(alarmSearch),alarmSearch.getTelegramId());
        }

        /*
        for(int i = 0; i < newsAlarm.size(); i++){
            AlarmSearch alarmSearch = newsAlarm.get(i);
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(alarmSearch),alarmSearch.getTelegramId());
        }*/

        return new CommonResponseDto();
    }

    public CommonResponseDto sendRecommendAlarm(){
        List<Long> userIdArr = userRepository.findUserId();

        /*
        for(int i = 0; i < userIdArr.size(); i++){
            externalAlarmClient.sendContentsMessage(getUserRecommendAlarmContents(userIdArr.get(i),CategoryType.YOUTUBE), userIdArr.get(i));
            externalAlarmClient.sendContentsMessage(getUserRecommendAlarmContents(userIdArr.get(i),CategoryType.NEWS), userIdArr.get(i));
            externalAlarmClient.sendJobMessage(getUserRecommendAlarmJobContents(userIdArr.get(i)),userIdArr.get(i));
        }*/

        return new CommonResponseDto();
    }

    public List<AlarmDtoInterface> getUserYoutubeAndNewsAlarmContents(AlarmSearch alarmSearch){
        List<YoutubeAndNews> youtubeAndNewsAlarmList = new ArrayList<>();

        youtubeAndNewsAlarmList = youtubeAndNewsRepository.findYoutubeAndNewsByUrlGreater(crawlingContentsLastUrlRepository.findByContentsProvider(alarmSearch.getContentsProvider()).get().getLastUrl(),alarmSearch.getContentsProvider());

        return youtubeAndNewsAlarmList.stream()
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<JobAlarmDto> getUserJobAlarmContents(long userIdx){
        List<Subscribe> subscribe = subscribeRepository.findByUserIdAndCategoryAndIsActive(userIdx,CategoryType.JOB,ActiveType.ACTIVE);
        int userExperienceYear = userRepository.findUsersById(userIdx).getYearOfExperience();
        JobType jobType = userRepository.findUsersById(userIdx).getJob();
        List<Job> jobAlarmList = new ArrayList<>();

        for(int i = 0; i < subscribe.size(); i++){
            jobAlarmList = jobRepository.findJobByUrlGreater(crawlingContentsLastUrlRepository.findByContentsProvider(subscribe.get(i).getContentsProvider()).get().getLastUrl(),subscribe.get(i).getContentsProvider(),GradeType.checkGradleType(userExperienceYear),jobType);
        }

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
