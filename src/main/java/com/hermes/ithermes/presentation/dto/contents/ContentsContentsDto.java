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

    private String description;
    

    @Override
    public ContentsContentsDto convertEntity(ContentsEntityInterface contentsEntityInterface) {
        if(contentsEntityInterface instanceof YoutubeAndNews){
            return ContentsContentsDto.builder()
                    .title(((YoutubeAndNews) contentsEntityInterface).getTitle())
                    .image(((YoutubeAndNews) contentsEntityInterface).getImage())
                    .url(((YoutubeAndNews) contentsEntityInterface).getUrl())
                    .category(((YoutubeAndNews) contentsEntityInterface).getService().getCategory().getName())
                    .service(((YoutubeAndNews) contentsEntityInterface).getService().getName())
                    .contentsDate(((YoutubeAndNews) contentsEntityInterface).getContentsDate())
                    .description(((YoutubeAndNews) contentsEntityInterface).getDescription())
                    .build();
        }else if(contentsEntityInterface instanceof Job){
            return ContentsContentsDto.builder()
                    .title(((Job) contentsEntityInterface).getTitle())
                    .image(null)
                    .url(((Job) contentsEntityInterface).getUrl())
                    .category(((Job) contentsEntityInterface).getService().getCategory().getName())
                    .service(((Job) contentsEntityInterface).getService().getName())
                    .contentsDate(((Job) contentsEntityInterface).getEndDate())
                    .description(((Job) contentsEntityInterface).getLocation())
                    .build();
        }
        return null;
    }

}
