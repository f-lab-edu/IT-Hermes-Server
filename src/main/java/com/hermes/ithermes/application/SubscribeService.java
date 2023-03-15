package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.factory.SubscribeFactory;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeContentsDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<SubscribeContentsDto> findSubscribe(SubscribeFindSubscribeRequestDto subscribeFindSubScribeRequestDto) {
        List<Subscribe> subscribes = subscribeFactory.parseFindSubscribeDtoToSubscribes(subscribeFindSubScribeRequestDto);
        List<SubscribeContentsDto> contentsProviderTypes = subscribeFactory.findActiveContentsProviderType(subscribes);

        return contentsProviderTypes;
    }
}
