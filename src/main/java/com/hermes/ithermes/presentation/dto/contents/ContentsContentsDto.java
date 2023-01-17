package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
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
public class ContentsContentsDto implements ContentsDtoInterface {

    public String title;

    public String image;

    public String url;

    public String category;

    public ContentsProviderType contentProvider;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    public String description;

    public ContentsContentsDto(YoutubeAndNews youtubeAndNews) {
        this.title = youtubeAndNews.getTitle();
        this.image = youtubeAndNews.getImage();
        this.url = youtubeAndNews.getUrl();
        this.category = youtubeAndNews.getContentsProvider().getCategory().getTitle();
        this.contentProvider = youtubeAndNews.getContentsProvider().getName();
        this.contentsDate = youtubeAndNews.getContentsStartAt();
        this.description = youtubeAndNews.getDescription();
    }

    public ContentsContentsDto(Job job) {
        this.title = job.getTitle();
        this.image = null;
        this.url = job.getUrl();
        this.category = job.getContentsProvider().getCategory().getTitle();
        this.contentProvider = job.getContentsProvider().getName();
        this.contentsDate = job.getContentsEndAt();
        this.description = job.getCompany();
    }

    @Override
    public ContentsContentsDto convertEntityToDto(ContentsEntityInterface contentsEntityInterface) {
        if(contentsEntityInterface instanceof YoutubeAndNews){
            return new ContentsContentsDto((YoutubeAndNews) contentsEntityInterface);
        }
        return new ContentsContentsDto((Job) contentsEntityInterface);
    }

}
