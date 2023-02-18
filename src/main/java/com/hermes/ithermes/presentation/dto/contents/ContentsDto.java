package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.CrawlingContents;
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

    public ContentsDto(CrawlingContents crawlingContents){
        this.title = crawlingContents.findTitle();
        this.image = crawlingContents.findImage();
        this.url = crawlingContents.findUrl();
        this.category = crawlingContents.findCategoryType();
        this.contentProvider = crawlingContents.findContentsProvider();
        this.contentsDate = crawlingContents.findContentsTime();
        this.description = crawlingContents.findDescription();
    }

    @Override
    public ContentsDto convertEntityToDto(CrawlingContents crawlingContents) {
        return new ContentsDto(crawlingContents);
    }

}
