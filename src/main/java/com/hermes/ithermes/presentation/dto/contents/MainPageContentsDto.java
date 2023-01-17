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
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MainPageContentsDto implements ContentsDtoInterface {

    public String title;

    public String image;

    public String url;

    public String category;

    public ContentsProviderType contentsProviderType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    public MainPageContentsDto(YoutubeAndNews youtubeAndNews) {
        this.title = youtubeAndNews.getTitle();
        this.image = youtubeAndNews.getImage();
        this.url = youtubeAndNews.getUrl();
        this.category = youtubeAndNews.getContentsProvider().getCategory().getTitle();
        this.contentsProviderType = youtubeAndNews.getContentsProvider().getName();
        this.contentsDate = youtubeAndNews.getContentsStartAt();
    }

    public MainPageContentsDto(Job job) {
        this.title = job.getTitle();
        this.image = null;
        this.url = job.getUrl();
        this.category = job.getContentsProvider().getCategory().getTitle();
        this.contentsProviderType = job.getContentsProvider().getName();
        this.contentsDate = job.getContentsEndAt();
    }

    @Override
    public MainPageContentsDto convertEntityToDto(ContentsEntityInterface contentsEntityInterface) {
        if(contentsEntityInterface instanceof YoutubeAndNews){
            return new MainPageContentsDto((YoutubeAndNews) contentsEntityInterface);
        }
        return new MainPageContentsDto((Job) contentsEntityInterface);
    }

}
