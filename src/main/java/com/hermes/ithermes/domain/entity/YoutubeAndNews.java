package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.ElasticSearchType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeAndNews extends BaseEntity implements CrawlingContents {

    @Version
    private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

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

    @Enumerated(EnumType.STRING)
    private ElasticSearchType elasticSearchType;

    public void updateViewCount() {
        this.viewCount += +1L;
    }

    public void updateElasticSearchType() { this.elasticSearchType=ElasticSearchType.DONE; }

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

    public boolean isContainRecommendKeywords(List<String> keywordList){
        return keywordList.stream()
                .anyMatch(m->m.contains(title));
    }

    @Override
    public Long findViewCount() {
        return viewCount;
    }

    public static YoutubeAndNewsSearch convertESentity(YoutubeAndNews youtubeAndNews){
        return YoutubeAndNewsSearch.builder()
                .title(youtubeAndNews.getTitle())
                .description(youtubeAndNews.getDescription())
                .image(youtubeAndNews.getImage())
                .url(youtubeAndNews.getUrl())
                .contentsStartAt(youtubeAndNews.getContentsStartAt())
                .viewCount(youtubeAndNews.getViewCount())
                .isDelete(youtubeAndNews.getIsDelete())
                .category(youtubeAndNews.getCategory())
                .contentsProvider(youtubeAndNews.getContentsProvider())
                .build();

    }


}
