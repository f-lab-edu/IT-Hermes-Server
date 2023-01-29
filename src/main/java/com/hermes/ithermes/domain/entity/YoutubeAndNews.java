package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeAndNews extends BaseEntity implements ContentsEntityInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
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

    public void initDefaultData() {
        viewCount=0L;
    }

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
}
