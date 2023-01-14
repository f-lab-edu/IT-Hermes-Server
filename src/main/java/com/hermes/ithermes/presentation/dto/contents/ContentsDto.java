package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentsDto implements DtoInterface<EntityInterface,ContentsDto>{

    public String title;

    public String image;

    public String url;

    public String category;

    public String service;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    private String description;

    @Override
    public ContentsDto convertEntity(EntityInterface entityInterface) {
        if(entityInterface instanceof YoutubeAndNews){
            return ContentsDto.builder()
                    .title(((YoutubeAndNews) entityInterface).getTitle())
                    .image(((YoutubeAndNews) entityInterface).getImage())
                    .url(((YoutubeAndNews) entityInterface).getUrl())
                    .category(((YoutubeAndNews) entityInterface).getService().getCategory().getName())
                    .service(((YoutubeAndNews) entityInterface).getService().getName())
                    .contentsDate(((YoutubeAndNews) entityInterface).getContentsDate())
                    .description(((YoutubeAndNews) entityInterface).getDescription())
                    .build();
        }else if(entityInterface instanceof Job){
            return ContentsDto.builder()
                    .title(((Job) entityInterface).getTitle())
                    .image(null)
                    .url(((Job) entityInterface).getUrl())
                    .category(((Job) entityInterface).getService().getCategory().getName())
                    .service(((Job) entityInterface).getService().getName())
                    .contentsDate(((Job) entityInterface).getEndDate())
                    .description(((Job) entityInterface).getLocation())
                    .build();
        }
        return null;
    }

}
