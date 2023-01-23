package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
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
            externalAlarmClient.sendYoutubeAndNewsMessage(getYoutubeAndNewsAlarmDto(i), userIdArr.get(i));
            externalAlarmClient.sendJobMessage(getJobAlarmDto(i), userIdArr.get(i));
        }

        return new CommonResponseDto();
    }

    public List<YoutubeAndNewsAlarmDto> getYoutubeAndNewsAlarmDto(long userIdx){
        List<ContentsProvider> contentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx);

        List<YoutubeAndNewsAlarmDto> youtubeAndNewsAlarmDtoList = contentsProviderList.stream()
                .filter(m -> m.getName().equals(CategoryType.YOUTUBE) || m.getName().equals(CategoryType.NEWS))
                .map(m -> youtubeAndNewsJpaRepository.findYoutubeAndNewsByContentsProvider(m))
                .flatMap(List::stream)
                .map(m -> YoutubeAndNewsAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());

        return youtubeAndNewsAlarmDtoList;
    }

    public List<JobAlarmDto> getJobAlarmDto(long userIdx){
        List<ContentsProvider> contentsProviderList = subscribeRepository.findContentsProvider(ActiveType.ACTIVE, userIdx);

        List<JobAlarmDto> jobAlarmDtoList = contentsProviderList.stream()
                .filter(m -> m.getName().equals(CategoryType.NEWS))
                .map(m -> jobJpaRepository.findJobByContentsProvider(m))
                .flatMap(List::stream)
                .map(m -> JobAlarmDto.convertEntityToDto(m))
                .collect(Collectors.toList());

        return jobAlarmDtoList;
    }

}
