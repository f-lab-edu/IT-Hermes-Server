package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.ContentsProviderRepository;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsCreateRequestDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@Component
@RequiredArgsConstructor
public class YoutubeAndNewsFactory {
    private final ContentsProviderRepository contentsProviderRepository;

    public List<YoutubeAndNews> parseLoginRequestDtoToUser(List<YoutubeAndNewsCreateRequestDto> youtubeAndNewsCreateRequestDtoList) {
        List<YoutubeAndNews> youtubeAndNewsList = new ArrayList<>();
        Optional<ContentsProvider> contentsProvider = contentsProviderRepository.findByName(ContentsProviderType.YOZM); // 인자로 요즘 IT 데이터도 전달 필요!
        ContentsProvider provider = contentsProvider.get();
        youtubeAndNewsCreateRequestDtoList.stream().forEach(v-> {
            String title = v.getTitle();
            String description = v.getDescription();
            String thumbnail = v.getThumbnail();
            String url = v.getUrl();
            LocalDateTime date = LocalDateTime.of(2022, Month.AUGUST,21,13,45,20);
            YoutubeAndNews youtubeAndNews = YoutubeAndNews.builder()
                    .contentsProvider(provider)
                    .description(description)
                    .title(title)
                    .image(thumbnail)
                    .url(url)
                    .contentsStartAt(date)
                    .isDelete(false)
                    .viewCount(0L)
                    .build();
            youtubeAndNewsList.add(youtubeAndNews);
        });
        return youtubeAndNewsList;
    }
}
