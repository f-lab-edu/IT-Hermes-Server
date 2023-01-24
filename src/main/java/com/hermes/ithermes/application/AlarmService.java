package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.JobJpaRepository;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

    private final ExternalAlarmClient externalAlarmClient;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository;
    private final JobJpaRepository jobJpaRepository;

    public CommonResponseDto sendAlarm() {
        List<Long> userIdArr = userRepository.findByTelegramIdIsNotNull().stream()
                .map(m -> m.getId())
                .collect(Collectors.toList());

        for (int i = 0; i < userIdArr.size(); i++) {
            externalAlarmClient.sendYoutubeMessage(getUserYoutubeAlarmContents(userIdArr.get(i)), userIdArr.get(i));
            externalAlarmClient.sendNewsMessage(getUserNewsAlarmContents(userIdArr.get(i)), userIdArr.get(i));
            externalAlarmClient.sendJobMessage(getUserJobAlarmContents(userIdArr.get(i)), userIdArr.get(i));
        }

        return new CommonResponseDto();
    }

    public List<YoutubeAndNewsAlarmDto> getUserYoutubeAlarmContents(long userIdx){
        List<ContentsProviderType> youtubeContentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx, CategoryType.YOUTUBE);

        List<YoutubeAndNewsAlarmDto> youtubeAlarmDtoList = youtubeContentsProviderList.stream()
                .map(m -> youtubeAndNewsJpaRepository.findYoutubeAndNewsByContentsProvider(m))
                .flatMap(List::stream)
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());

        return youtubeAlarmDtoList;
    }

    public List<YoutubeAndNewsAlarmDto> getUserNewsAlarmContents(long userIdx){
        List<ContentsProviderType> newsContentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx, CategoryType.NEWS);

        List<YoutubeAndNewsAlarmDto> newsAlarmDtoList = newsContentsProviderList.stream()
                .map(m -> youtubeAndNewsJpaRepository.findYoutubeAndNewsByContentsProvider(m))
                .flatMap(List::stream)
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());

        return newsAlarmDtoList;
    }

    public List<JobAlarmDto> getUserJobAlarmContents(long userIdx){
        List<ContentsProviderType> jobContentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx, CategoryType.JOB);

        List<JobAlarmDto> jobAlarmDtoList = jobContentsProviderList.stream()
                .map(m -> jobJpaRepository.findJobByContentsProvider(m))
                .flatMap(List::stream)
                .map(m -> JobAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());

        return jobAlarmDtoList;
    }

}
