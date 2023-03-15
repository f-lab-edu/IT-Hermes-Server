package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeContentsDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Component
@RequiredArgsConstructor
public class SubscribeFactory {
    private final SubscribeRepository subscribeRepository;
    private final UserFactory userFactory;

    public List<Subscribe> parsePutSubscribeDtoToSubscribes(SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto) {
        List<Subscribe> subscribes = new ArrayList<>();
        String loginId = subscribePutSubscribeRequestDto.getId();
        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
        ArrayList<SubscribeContentsDto> subscribeContentsList = subscribePutSubscribeRequestDto.getSubscribeContentsList();
        boolean isEdit = existsByUserId(user.getId());

        subscribeContentsList.stream().forEach(v -> {
            ContentsProviderType contentsProviderType = ContentsProviderType.valueOf(v.getContentsProvider());
            CategoryType categoryType = CategoryType.findByContentsProviderType(contentsProviderType);
            ActiveType activeType = ActiveType.valueOf(v.getIsActive());

            Subscribe subscribe = CategoryType.parseSubscribe(user, categoryType, contentsProviderType, activeType);
            if (isEdit) {
                Subscribe editSubscribe = subscribeRepository.findByContentsProvider(ContentsProviderType.valueOf(v.getContentsProvider())).orElseThrow(() -> new EnumTypeFormatException());
                editSubscribe.changeUpdateAt(ActiveType.valueOf(v.getIsActive()));
            } else {
                subscribes.add(subscribe);
            }

        });
        return subscribes;
    }

    public List<Subscribe> parseFindSubscribeDtoToSubscribes(SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto) {
        String loginId = subscribeFindSubscribeRequestDto.getId();
        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
        Long userId = user.getId();
        return findSubscribeByUserId(userId).orElseThrow(null);
    }

    public List<SubscribeContentsDto> findActiveContentsProviderType(List<Subscribe> subscribes) {
        return subscribes.stream().map(v -> new SubscribeContentsDto(v.getContentsProvider().getTitle(), v.getIsActive().getTitle())).collect(Collectors.toList());
    }

    public Optional<List<Subscribe>> findSubscribeByUserId(Long userId) {
        return subscribeRepository.findByUserId(userId);
    }

    public boolean existsByUserId(Long userId) {
        return subscribeRepository.existsByUserId(userId);
    }
}



