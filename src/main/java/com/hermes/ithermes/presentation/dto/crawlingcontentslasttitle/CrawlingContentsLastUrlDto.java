package com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import com.hermes.ithermes.domain.util.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CrawlingContentsLastUrlDto {
    ContentsProviderType contentsProvider;
    String lastUrl;
    JobType job;
    GradeType grade;
}
