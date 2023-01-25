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
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MainPageContentsDto implements ContentsDtoInterface {

    public String title;

    public String image;

    public String url;

    public CategoryType category;

    public ContentsProviderType contentsProviderType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    public MainPageContentsDto(ContentsEntityInterface contentsEntityInterface){
        this.title = contentsEntityInterface.title();
        this.image = contentsEntityInterface.image();
        this.url = contentsEntityInterface.url();
        this.category = contentsEntityInterface.categoryType();
        this.contentsProviderType = contentsEntityInterface.contentsProvider();
        this.contentsDate = contentsEntityInterface.contentsTime();
    }

    @Override
    public MainPageContentsDto convertEntityToDto(ContentsEntityInterface contentsEntityInterface) {
        return new MainPageContentsDto(contentsEntityInterface);
    }

}
