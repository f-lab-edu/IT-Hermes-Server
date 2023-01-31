package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.factory.YoutubeAndNewsFactory;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsInsertDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsLastUrlRequestDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsLastUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeAndNewsService {
    private final YoutubeAndNewsFactory youtubeAndNewsFactory;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;

    public CommonResponseDto parseYoutubeAndNews(YoutubeAndNewsInsertDto youtubeAndNewsCrawlingDtoList) {
        List<YoutubeAndNews> youtubeAndNewsList = youtubeAndNewsFactory.parseYoutubeAndNews(youtubeAndNewsCrawlingDtoList);
        youtubeAndNewsList.stream().forEach(v-> youtubeAndNewsRepository.save(v));
        return new CommonResponseDto();
    }

    public YoutubeAndNewsLastUrlResponseDto findYoutubeAndNewsLastUrl(YoutubeAndNewsLastUrlRequestDto youtubeAndNewsLastUrlRequestDto) {
       return youtubeAndNewsFactory.findYoutubeAndNewsLastUrl(youtubeAndNewsLastUrlRequestDto);
    }
}
