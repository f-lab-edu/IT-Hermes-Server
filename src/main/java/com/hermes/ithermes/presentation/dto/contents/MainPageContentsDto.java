package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.sun.tools.javac.Main;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MainPageContentsDto implements DtoInterface<EntityInterface,MainPageContentsDto>{

    public String title;

    public String image;

    public String url;

    public String category;

    public String service;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    @Override
    public MainPageContentsDto convertEntity(EntityInterface entityInterface) {
        if(entityInterface instanceof YoutubeAndNews){
            return MainPageContentsDto.builder()
                    .title(((YoutubeAndNews) entityInterface).getTitle())
                    .image(((YoutubeAndNews) entityInterface).getImage())
                    .url(((YoutubeAndNews) entityInterface).getUrl())
                    .category(((YoutubeAndNews) entityInterface).getService().getCategory().getName())
                    .service(((YoutubeAndNews) entityInterface).getService().getName())
                    .contentsDate((((YoutubeAndNews) entityInterface).getContentsDate()))
                    .build();
        }if(entityInterface instanceof Job){
            return MainPageContentsDto.builder()
                    .title(((Job) entityInterface).getTitle())
                    .image(null)
                    .url(((Job) entityInterface).getUrl())
                    .category(((Job) entityInterface).getService().getCategory().getName())
                    .service(((Job) entityInterface).getService().getName())
                    .contentsDate(((Job) entityInterface).getEndDate())
                    .build();
        }
        return null;
    }

}
