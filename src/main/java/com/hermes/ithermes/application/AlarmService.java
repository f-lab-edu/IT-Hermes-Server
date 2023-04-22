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

    public CommonResponseDto sendSubscribeAlarm() {
        List<Long> userIdArr = userRepository.findUserId();

        for (int i = 0; i < userIdArr.size(); i++) {
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(userIdArr.get(i),CategoryType.YOUTUBE), userIdArr.get(i));
            externalAlarmClient.sendContentsMessage(getUserYoutubeAndNewsAlarmContents(userIdArr.get(i),CategoryType.NEWS), userIdArr.get(i));
            //externalAlarmClient.sendJobMessage(getUserJobAlarmContents(userIdArr.get(i)), userIdArr.get(i));
        }

        return new CommonResponseDto();
    }

    public CommonResponseDto sendRecommendAlarm(){
        List<Long> userIdArr = userRepository.findUserId();

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
            List<YoutubeAndNews> tmpYoutube = youtubeAndNewsRepository.findYoutubeAndNewsByUrlGreater(crawlingContentsLastUrlRepository.findByContentsProvider(subscribe.get(i).getContentsProvider()).get().getLastUrl(),subscribe.get(i).getContentsProvider());
            tmpYoutube.stream().forEach(v ->
                    youtubeAndNewsAlarmList.add(v));
            System.out.println("-----------");
            System.out.println(subscribe.get(i).getContentsProvider());
            System.out.println(youtubeAndNewsAlarmList.size());
        }

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
