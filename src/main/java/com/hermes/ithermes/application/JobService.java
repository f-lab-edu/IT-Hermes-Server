package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.CrawlingContentsLastUrl;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.exception.NoCrawlingDataException;
import com.hermes.ithermes.domain.factory.CrawlingContentsLastUrlFactory;
import com.hermes.ithermes.domain.factory.JobFactory;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.infrastructure.CrawlingContentsLastUrlRepository;
import com.hermes.ithermes.infrastructure.JobJpaRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.job.JobInsertRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobService {
    private final JobFactory jobFactory;
    private final JobJpaRepository jobJpaRepository;
    private final CrawlingContentsLastUrlFactory crawlingContentsLastUrlFactory;
    private final CrawlingContentsLastUrlRepository crawlingContentsLastUrlRepository;

    @Transactional
    public CommonResponseDto insertJob(JobInsertRequestDto jobInsertRequestDto) {
        if (jobInsertRequestDto.getJobCrawlingDtoList().isEmpty()) throw new NoCrawlingDataException();

        List<Job> jobList = jobFactory.insertJob(jobInsertRequestDto);
        jobList.stream().forEach(v -> jobJpaRepository.save(v));

        Job recentJob = jobList.get(0);

        ContentsProviderType contentsProvider = recentJob.getContentsProvider();
        GradeType grade = recentJob.getGrade();
        JobType jobType = jobInsertRequestDto.getJob();

        Optional<CrawlingContentsLastUrl> contentsLastTitle = crawlingContentsLastUrlRepository.findByContentsProviderAndGradeAndJob(contentsProvider, grade, jobType);
        CrawlingContentsLastUrl recentCrawlingContentsLastUrl = crawlingContentsLastUrlFactory.parseCrawlingContentsLastUrlToJob(recentJob, jobType);

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
