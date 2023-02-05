package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.CrawlingContentsLastUrl;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.exception.NoCrawlingDataException;
import com.hermes.ithermes.domain.factory.CrawlingContentsLastUrlFactory;
import com.hermes.ithermes.domain.factory.YoutubeAndNewsFactory;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.CrawlingContentsLastUrlRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YoutubeAndNewsService {
    private final YoutubeAndNewsFactory youtubeAndNewsFactory;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final CrawlingContentsLastUrlFactory crawlingContentsLastUrlFactory;
    private final CrawlingContentsLastUrlRepository crawlingContentsLastUrlRepository;

    @Transactional
    public CommonResponseDto insertYoutubeAndNews(YoutubeAndNewsInsertDto youtubeAndNewsCrawlingDtoList) {
        if(youtubeAndNewsCrawlingDtoList.getYoutubeAndNewsCrawlingDtoList().isEmpty()) throw new NoCrawlingDataException();

        List<YoutubeAndNews> youtubeAndNewsList = youtubeAndNewsFactory.parseYoutubeAndNews(youtubeAndNewsCrawlingDtoList);
        youtubeAndNewsList.stream().forEach(v -> youtubeAndNewsRepository.save(v));

        YoutubeAndNews recentYoutubeAndNews = youtubeAndNewsList.get(0);

        ContentsProviderType contentsProvider = recentYoutubeAndNews.getContentsProvider();
        Optional<CrawlingContentsLastUrl> contentsLastTitle = crawlingContentsLastUrlRepository.findByContentsProvider(contentsProvider);
        CrawlingContentsLastUrl recentCrawlingContentsLastUrl = crawlingContentsLastUrlFactory.parseCrawlingContentsLastUrlToYoutubeAndNews(recentYoutubeAndNews);

        contentsLastTitle.ifPresentOrElse(
                v -> {
                    v.updateLastUrl(recentCrawlingContentsLastUrl);
                },
                () -> {
                    crawlingContentsLastUrlRepository.save(recentCrawlingContentsLastUrl);
                }
        );
        return new CommonResponseDto();
    }
}
