package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.factory.SubscribeFactory;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.ElasticSearchType;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.infrastructure.elastic.AlarmSearchRepository;
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
    private final AlarmSearchRepository alarmSearchRepository;

    @Transactional
    public CommonResponseDto putSubscribe(SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto) {
        List<Subscribe> subscribes = subscribeFactory.parsePutSubscribeDtoToSubscribes(subscribePutSubscribeRequestDto);
        subscribes.stream().forEach(v -> subscribeRepository.save(v));
        return new CommonResponseDto();
    }

    public List<SubscribeContentsDto> findSubscribe(SubscribeFindSubscribeRequestDto subscribeFindSubScribeRequestDto) {
        List<Subscribe> subscribes = subscribeFactory.parseFindSubscribeDtoToSubscribes(subscribeFindSubScribeRequestDto);
        List<SubscribeContentsDto> contentsProviderTypes = SubscribeFactory.findActiveContentsProviderType(subscribes);

        return contentsProviderTypes;
    }

    public void updateElasticsearch(){
        List<Subscribe> subscribesList = subscribeRepository.findAlarmJoin(ActiveType.ACTIVE);
        subscribesList.stream()
                .map(v -> Subscribe.convertESentity(v))
                .forEach(v ->
                    alarmSearchRepository.save(v)
                );
    }
}
