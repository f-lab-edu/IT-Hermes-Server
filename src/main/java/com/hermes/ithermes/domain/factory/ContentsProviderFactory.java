package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.infrastructure.ContentsProviderRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Component
@RequiredArgsConstructor
public class ContentsProviderFactory {
    private final ContentsProviderRepository contentsProviderRepository;

    public List<ContentsProvider> findAllContentsProvider() {
        return contentsProviderRepository.findAll();
    }
}
