package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.CrawlingContentsLastUrl;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.exception.NoCrawlingDataException;
import com.hermes.ithermes.domain.factory.CrawlingContentsLastUrlFactory;
import com.hermes.ithermes.domain.factory.YoutubeAndNewsFactory;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.CrawlingContentsLastUrlRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class YoutubeAndNewsService {
    private final YoutubeAndNewsFactory youtubeAndNewsFactory;
    private final YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository;
    private final CrawlingContentsLastUrlFactory crawlingContentsLastUrlFactory;
    private final CrawlingContentsLastUrlRepository crawlingContentsLastUrlRepository;

    public CommonResponseDto insertYoutubeAndNews(YoutubeAndNewsInsertDto youtubeAndNewsCrawlingDtoList) {
        if(youtubeAndNewsCrawlingDtoList.getYoutubeAndNewsCrawlingDtoList().isEmpty()) throw new NoCrawlingDataException();

        List<YoutubeAndNews> youtubeAndNewsList = youtubeAndNewsFactory.parseYoutubeAndNews(youtubeAndNewsCrawlingDtoList);
        youtubeAndNewsList.stream().forEach(v -> youtubeAndNewsJpaRepository.save(v));

        YoutubeAndNews recentYoutubeAndNews = youtubeAndNewsList.get(0);

        ContentsProviderType contentsProvider = recentYoutubeAndNews.getContentsProvider();
        Optional<CrawlingContentsLastUrl> contentsLastTitle = crawlingContentsLastUrlRepository.findByContentsProvider(contentsProvider);
        CrawlingContentsLastUrl recentCrawlingContentsLastUrl = crawlingContentsLastUrlFactory.parseCrawlingContentsLastUrlToYoutubeAndNews(recentYoutubeAndNews);

        contentsLastTitle.ifPresentOrElse(
                v -> {
                    v.change(recentCrawlingContentsLastUrl);
                },
                () -> {
                    crawlingContentsLastUrlRepository.save(recentCrawlingContentsLastUrl);
                }
        );
        return new CommonResponseDto();
    }
}
