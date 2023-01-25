package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.factory.CrawlingContentsLastUrlFactory;
import com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle.CrawlingContentsLastUrlDto;
import com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle.CrawlingContentsLastUrlFindAllResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingContentsLastUrlService {
    private final CrawlingContentsLastUrlFactory crawlingContentsLastUrlFactory;

    public CrawlingContentsLastUrlFindAllResponseDto findAllCrawlingContentsLastTitle() {
        List<CrawlingContentsLastUrlDto> crawlingContentsLastTitles = crawlingContentsLastUrlFactory.parseAllCrawlingContentsLastTitle();
        return new CrawlingContentsLastUrlFindAllResponseDto(crawlingContentsLastTitles);
    }
}
