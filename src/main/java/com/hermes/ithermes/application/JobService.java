package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.CrawlingContentsLastUrl;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.exception.NoCrawlingDataException;
import com.hermes.ithermes.domain.factory.CrawlingContentsLastUrlFactory;
import com.hermes.ithermes.domain.factory.JobFactory;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.CrawlingContentsLastUrlRepository;
import com.hermes.ithermes.infrastructure.JobJpaRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.job.JobInsertRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobFactory jobFactory;
    private final JobJpaRepository jobJpaRepository;
    private final CrawlingContentsLastUrlFactory crawlingContentsLastUrlFactory;
    private final CrawlingContentsLastUrlRepository crawlingContentsLastUrlRepository;

    public CommonResponseDto parseJob(JobInsertRequestDto jobInsertRequestDto) {
        if(jobInsertRequestDto.getJobCrawlingDtoList().isEmpty()) throw new NoCrawlingDataException();

        List<Job> jobList = jobFactory.parseJob(jobInsertRequestDto);
        jobList.stream().forEach(v-> jobJpaRepository.save(v));

        Job recentJob = jobList.get(0);

        ContentsProviderType contentsProvider = recentJob.getContentsProvider();
        Optional<CrawlingContentsLastUrl> contentsLastTitle = crawlingContentsLastUrlRepository.findByContentsProvider(contentsProvider);
        CrawlingContentsLastUrl recentCrawlingContentsLastUrl = crawlingContentsLastUrlFactory.parseCrawlingContentsLastUrlToJob(recentJob);

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
