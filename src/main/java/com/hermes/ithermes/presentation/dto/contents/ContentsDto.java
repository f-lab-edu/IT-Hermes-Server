package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentsDto implements ContentsDtoInterface {

    public String title;

    public String image;

    public String url;

    public CategoryType category;

    public ContentsProviderType contentProvider;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    public String description;

    public Long viewCnt;

    public ContentsDto(YoutubeAndNews youtubeAndNews) {
        this.title = youtubeAndNews.getTitle();
        this.image = youtubeAndNews.getImage();
        this.url = youtubeAndNews.getUrl();
        this.category = youtubeAndNews.getCategory();
        this.contentProvider = youtubeAndNews.getContentsProvider();
        this.contentsDate = youtubeAndNews.getContentsStartAt();
        this.description = youtubeAndNews.getDescription();
        this.viewCnt = youtubeAndNews.getViewCount();
    }

    public ContentsDto(Job job) {
        this.title = job.getTitle();
        this.image = null;
        this.url = job.getUrl();
        this.category = CategoryType.JOB;
        this.contentProvider = job.getContentsProvider();
        this.contentsDate = job.getContentsEndAt();
        this.description = job.getCompany();
        this.viewCnt = job.getViewCount();
    }

    @Override
    public ContentsDto convertEntityToDto(ContentsEntityInterface contentsEntityInterface) {
        if(contentsEntityInterface instanceof YoutubeAndNews){
            return new ContentsDto((YoutubeAndNews) contentsEntityInterface);
        }
        return new ContentsDto((Job) contentsEntityInterface);
    }

}
