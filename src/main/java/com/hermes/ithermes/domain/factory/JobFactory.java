package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import com.hermes.ithermes.presentation.dto.job.JobCrawlingDto;
import com.hermes.ithermes.presentation.dto.job.JobInsertRequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JobFactory {

    public List<Job> insertJob(JobInsertRequestDto jobInsertRequestDto) {
        List<Job> jobList = new ArrayList<>();
        List<JobCrawlingDto> jobCrawlingDtoList = jobInsertRequestDto.getJobCrawlingDtoList();
        ContentsProviderType contentsProvider = jobInsertRequestDto.getContentsProvider();
        GradeType grade = jobInsertRequestDto.getGrade();
        jobCrawlingDtoList.stream().forEach(v -> {
            String company = v.getCompany();
            String title = v.getTitle();
            String url = v.getUrl();
            String location = v.getLocation();
            String[] startDateArray = v.getStartDate().split("-");
            String[] endDateArray = v.getStartDate().split("-");
            LocalDateTime startDate = LocalDateTime.of(
                    Integer.parseInt(startDateArray[0]),
                    Integer.parseInt(startDateArray[1]),
                    Integer.parseInt(startDateArray[2]),
                    Integer.parseInt(startDateArray[3]),
                    Integer.parseInt(startDateArray[4]),
                    Integer.parseInt(startDateArray[5]));

            LocalDateTime endDate = LocalDateTime.of(
                    Integer.parseInt(endDateArray[0]),
                    Integer.parseInt(endDateArray[1]),
                    Integer.parseInt(endDateArray[2]),
                    Integer.parseInt(endDateArray[3]),
                    Integer.parseInt(endDateArray[4]),
                    Integer.parseInt(endDateArray[5]));

            Job job = Job
                    .builder()
                    .company(company)
                    .title(title)
                    .url(url)
                    .location(location)
                    .grade(grade)
                    .contentsStartAt(startDate)
                    .contentsEndAt(endDate)
                    .isDelete(false)
                    .viewCount(0L)
                    .contentsProvider(contentsProvider)
                    .build();

            jobList.add(job);
        });
        return jobList;
    }
}
