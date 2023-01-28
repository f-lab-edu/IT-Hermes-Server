package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.factory.JobFactory;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.job.JobInsertRequestDto;
import com.hermes.ithermes.presentation.dto.job.JobLastUrlRequestDto;
import com.hermes.ithermes.presentation.dto.job.JobLastUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobFactory jobFactory;
    private final JobRepository jobRepository;

    public CommonResponseDto parseJob(JobInsertRequestDto jobInsertRequestDto) {
        List<Job> jobList = jobFactory.parseJob(jobInsertRequestDto);
        jobList.stream().forEach(v-> jobRepository.save(v));
        return new CommonResponseDto();
    }

    public JobLastUrlResponseDto findJobLastUrl(JobLastUrlRequestDto jobLastUrlRequestDto) {
        return jobFactory.findJobLastUrl(jobLastUrlRequestDto);
    }
}
