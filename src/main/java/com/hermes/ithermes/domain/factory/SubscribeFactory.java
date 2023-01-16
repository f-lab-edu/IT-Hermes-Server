package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Builder
@Component
@RequiredArgsConstructor
public class SubscribeFactory {
    private final SubscribeRepository subscribeRepository;
    private final ContentsProviderFactory contentsProviderFactory;
    private final UserFactory userFactory;

    public List<Subscribe> parsePutSubscribeDtoToSubscribes(SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto) {
        List<Subscribe> subscribes = new ArrayList<>();
        List<ContentsProvider> contentsProviders = contentsProviderFactory.findAllContentsProvider();
        String loginId = subscribePutSubscribeRequestDto.getId();
        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
        String minYearOfExperience = subscribePutSubscribeRequestDto.getMinYearOfExperience();
        String maxYearOfExperience = subscribePutSubscribeRequestDto.getMaxYearOfExperience();
        JobType jobType = subscribePutSubscribeRequestDto.getJob();

        int index = 0;
        for (String active : subscribePutSubscribeRequestDto.getKeywordList()) {
            ContentsProviderType contentsProviderType = ContentsProviderType.values()[index];
            ActiveType activeType = ActiveType.valueOf(active);
            ContentsProvider contentsProvider = contentsProviders.get(index);
            Long contentsProviderId = contentsProvider.getId();
            CategoryType categoryType = CategoryType.findByContentsProviderType(contentsProviderType);
            Optional<Subscribe> subscribeOptional = findByContentsProviderId(contentsProviderId);

            Subscribe subscribe = categoryType.getParseSubscribe().parseSubscribe(user, contentsProvider, activeType, jobType, minYearOfExperience, maxYearOfExperience);

            subscribeOptional.ifPresent(v -> {
                subscribe.changeUpdateAt();
            });
            subscribes.add(subscribe);
            index++;
        }
        return subscribes;
    }

    public List<Subscribe> parseFindSubscribeDtoToSubscribes(SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto) {
        String loginId = subscribeFindSubscribeRequestDto.getId();
        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
        Long userId = user.getId();
        List<Subscribe> subscribes = findByUserId(userId);
        return subscribes;
    }

    public List<String> findActiveContentsProviderType(List<Subscribe> subscribes) {
        return subscribes.stream().map(v -> v.getIsActive().getTitle()).toList();
    }

    public Subscribe findJobCategoryData(List<Subscribe> subscribes) {
        return subscribes.stream()
                .filter(v -> Objects.nonNull(v.getJob()) || Objects.nonNull(v.getMinYearOfExperience()) || Objects.nonNull(v.getMaxYearOfExperience()))
                .findAny().orElse(null);
    }

    public Optional<Subscribe> findByContentsProviderId(Long contentsProviderId) {
        return subscribeRepository.findByContentsProviderId(contentsProviderId);
    }

    public List<Subscribe> findByUserId(Long userId) {
        return subscribeRepository.findByUserId(userId).orElseThrow(() -> new WrongIdOrPasswordException());
    }
}



