package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.CrawlingContentsLastUrl;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.infrastructure.CrawlingContentsLastUrlRepository;
import com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle.CrawlingContentsLastUrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CrawlingContentsLastUrlFactory {
    private final CrawlingContentsLastUrlRepository crawlingContentsLastUrlRepository;

    public List<CrawlingContentsLastUrlDto> parseAllCrawlingContentsLastTitle() {

        List<CrawlingContentsLastUrlDto> crawlingContentsLastUrlDtoList = new ArrayList<>();

        List<CrawlingContentsLastUrl> crawlingContentsLastUrlRepositoryAll = crawlingContentsLastUrlRepository.findAll();
        crawlingContentsLastUrlRepositoryAll.stream().forEach(v -> {
            crawlingContentsLastUrlDtoList.add(new CrawlingContentsLastUrlDto(v.getContentsProvider(), v.getLastUrl(),v.getJob(),v.getGrade()));
        });
        return crawlingContentsLastUrlDtoList;
    }

    public CrawlingContentsLastUrl parseCrawlingContentsLastUrlToYoutubeAndNews(YoutubeAndNews youtubeAndNews) {
        return CrawlingContentsLastUrl.builder()
                .lastUrl(youtubeAndNews.getUrl())
                .contentsProvider(youtubeAndNews.getContentsProvider())
                .build();
    }

    public CrawlingContentsLastUrl parseCrawlingContentsLastUrlToJob(Job job, JobType jobType) {
        return CrawlingContentsLastUrl.builder()
                .lastUrl(job.getUrl())
                .grade(job.getGrade())
                .job(jobType)
                .contentsProvider(job.getContentsProvider())
                .build();
    }
}
