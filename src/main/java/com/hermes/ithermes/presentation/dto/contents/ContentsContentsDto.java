package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentsContentsDto implements ContentsDtoInterface {

    public String title;

    public String image;

    public String url;

    public String category;

    public String service;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    public String description;

    public ContentsContentsDto(YoutubeAndNews youtubeAndNews) {
        this.title = youtubeAndNews.getTitle();
        this.image = youtubeAndNews.getImage();
        this.url = youtubeAndNews.getUrl();
        this.category = youtubeAndNews.getService().getCategory().getName();
        this.service = youtubeAndNews.getService().getName();
        this.contentsDate = youtubeAndNews.getContentsDate();
        this.description = youtubeAndNews.getDescription();
    }

    public ContentsContentsDto(Job job) {
        this.title = job.getTitle();
        this.image = null;
        this.url = job.getUrl();
        this.category = job.getService().getCategory().getName();
        this.service = job.getService().getName();
        this.contentsDate = job.getEndDate();
        this.description = job.getCompany();
    }

    @Override
    public ContentsContentsDto convertEntityToDto(ContentsEntityInterface contentsEntityInterface) {
        if(contentsEntityInterface instanceof YoutubeAndNews){
            return new ContentsContentsDto(new YoutubeAndNews());
        }
        return new ContentsContentsDto(new Job());
    }

}
