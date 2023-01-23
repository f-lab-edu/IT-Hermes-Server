package com.hermes.ithermes.presentation.dto.alarm;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class JobAlarmDto {

    private String title;

    private String location;

    private String company;

    private String url;

    private LocalDateTime contentsEndAt;

    private ContentsProviderType contentsProviderType;

    public static JobAlarmDto convertEntityToDto(Job job){
        return JobAlarmDto.builder()
                .title(job.getTitle())
                .location(job.getLocation())
                .company(job.getCompany())
                .url(job.getUrl())
                .contentsEndAt(job.getContentsEndAt())
                .contentsProviderType(job.getContentsProvider().getName())
                .build();
    }

}
