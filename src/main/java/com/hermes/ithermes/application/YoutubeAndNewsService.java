package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.factory.YoutubeAndNewsFactory;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeAndNewsService {
    private final YoutubeAndNewsFactory youtubeAndNewsFactory;
    private final YoutubeAndNewsJpaRepository youtubeAndNewsRepository;

    public CommonResponseDto insertYoutubeAndNews(List<YoutubeAndNewsCreateRequestDto> youtubeAndNewsCreateRequestDtoList) {
        List<YoutubeAndNews> youtubeAndNewsList = youtubeAndNewsFactory.parseLoginRequestDtoToUser(youtubeAndNewsCreateRequestDtoList);
        youtubeAndNewsList.stream().forEach(v-> youtubeAndNewsRepository.save(v));
        return new CommonResponseDto();
    }
}
