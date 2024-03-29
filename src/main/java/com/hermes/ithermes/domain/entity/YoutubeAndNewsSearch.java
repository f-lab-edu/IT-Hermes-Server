package com.hermes.ithermes.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "youtubeandnewssearch")
public class YoutubeAndNewsSearch extends BaseEntity implements CrawlingContents {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    private String image;

    @Column(nullable = false)
    private String url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime contentsStartAt;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Boolean isDelete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentsProviderType contentsProvider;

    @Override
    public String findTitle() {
        return title;
    }

    @Override
    public String findImage() {
        return image;
    }

    @Override
    public String findUrl() {
        return url;
    }

    @Override
    public CategoryType findCategoryType() {
        return category;
    }

    @Override
    public ContentsProviderType findContentsProvider() {
        return contentsProvider;
    }

    @Override
    public LocalDateTime findContentsTime() {
        return contentsStartAt;
    }

    @Override
    public String findDescription() {
        return description;
    }

    @Override
    public Long findViewCount() {
        return viewCount;
    }


}
