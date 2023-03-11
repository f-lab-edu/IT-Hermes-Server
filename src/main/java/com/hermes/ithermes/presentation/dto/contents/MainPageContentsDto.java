package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MainPageContentsDto implements ContentsDtoInterface, Serializable {

    public String title;

    public String image;

    public String url;

    public CategoryType category;

    public ContentsProviderType contentsProviderType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime contentsDate;

    public Long viewCnt;

    public MainPageContentsDto(CrawlingContents crawlingContents) {
        this.title = crawlingContents.findTitle();
        this.image = crawlingContents.findImage();
        this.url = crawlingContents.findUrl();
        this.category = crawlingContents.findCategoryType();
        this.contentsProviderType = crawlingContents.findContentsProvider();
        this.contentsDate = crawlingContents.findContentsTime();
        this.viewCnt = crawlingContents.findViewCount();
    }

    @Override
    public MainPageContentsDto convertEntityToDto(CrawlingContents crawlingContents) {
        return new MainPageContentsDto(crawlingContents);
    }

}
