package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.factory.YoutubeAndNewsFactory;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
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
    private final YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository;

    public CommonResponseDto insertYoutubeAndNews(YoutubeAndNewsInsertDto youtubeAndNewsCrawlingDtoList) {
        List<YoutubeAndNews> youtubeAndNewsList = youtubeAndNewsFactory.insertYoutubeAndNews(youtubeAndNewsCrawlingDtoList);
        youtubeAndNewsList.stream().forEach(v-> youtubeAndNewsJpaRepository.save(v));
        return new CommonResponseDto();
    }

    public YoutubeAndNewsLastUrlResponseDto findYoutubeAndNewsLastUrl(YoutubeAndNewsLastUrlRequestDto youtubeAndNewsLastUrlRequestDto) {
       return youtubeAndNewsFactory.findYoutubeAndNewsLastUrl(youtubeAndNewsLastUrlRequestDto);
    }
}
