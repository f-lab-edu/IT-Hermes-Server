package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.factory.SubscribeFactory;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeResponseDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final SubscribeFactory subscribeFactory;

    @Transactional
    public CommonResponseDto putSubscribe(SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto) {
        List<Subscribe> subscribes = subscribeFactory.parsePutSubscribeDtoToSubscribes(subscribePutSubscribeRequestDto);
        subscribes.stream().forEach(v -> subscribeRepository.save(v));
        return new CommonResponseDto();
    }

    public SubscribeFindSubscribeResponseDto findSubscribe(SubscribeFindSubscribeRequestDto subscribeFindSubScribeRequestDto) {

        List<Subscribe> subscribes = subscribeFactory.parseFindSubscribeDtoToSubscribes(subscribeFindSubScribeRequestDto);
        List<String> contentsProviderTypes = subscribeFactory.findActiveContentsProviderType(subscribes);
        Optional<Subscribe> jobCategoryData = subscribeFactory.findJobCategoryData(subscribes);
        String job = String.valueOf(jobCategoryData.flatMap(v -> Optional.ofNullable(v.getJob())).orElse(null));
        String startDateOfExperience = String.valueOf(jobCategoryData.flatMap(v -> Optional.ofNullable(v.getMinYearOfExperience())).orElse(null));
        String endDateOfExperience = String.valueOf(jobCategoryData.flatMap(v -> Optional.ofNullable(v.getMaxYearOfExperience())).orElse(null));

        return new SubscribeFindSubscribeResponseDto(contentsProviderTypes, job, startDateOfExperience, endDateOfExperience);
    }
}
