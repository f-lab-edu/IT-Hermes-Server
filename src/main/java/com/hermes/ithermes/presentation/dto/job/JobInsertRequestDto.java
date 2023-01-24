package com.hermes.ithermes.presentation.dto.job;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JobInsertRequestDto {
    private ContentsProviderType contentsProvider;
    private GradeType grade;
    private List<JobCrawlingDto> jobCrawlingDtoList;
}