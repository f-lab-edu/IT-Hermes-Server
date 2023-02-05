package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
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

    public ContentsDto(ContentsEntityInterface contentsEntityInterface) {
        this.title = contentsEntityInterface.findTitle();
        this.image = contentsEntityInterface.findImage();
        this.url = contentsEntityInterface.findUrl();
        this.category = contentsEntityInterface.findCategoryType();
        this.contentProvider = contentsEntityInterface.findContentsProvider();
        this.contentsDate = contentsEntityInterface.findContentsTime();
        this.description = contentsEntityInterface.findDescription();
    }

    @Override
    public ContentsDto convertEntityToDto(ContentsEntityInterface contentsEntityInterface) {
        return new ContentsDto(contentsEntityInterface);
    }

}
