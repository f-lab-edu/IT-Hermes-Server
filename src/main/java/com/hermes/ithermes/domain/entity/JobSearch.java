package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.util.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "jobsearch")
public class JobSearch extends BaseEntity implements CrawlingContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String company;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime contentsStartAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime contentsEndAt;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Boolean isDelete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentsProviderType contentsProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GradeType grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private ElasticSearchType elasticSearchType;

    @Override
    public String findTitle() {
        return title;
    }

    @Override
    public String findImage() {
        return null;
    }

    @Override
    public String findUrl() {
        return url;
    }

    @Override
    public CategoryType findCategoryType() {
        return CategoryType.JOB;
    }

    @Override
    public ContentsProviderType findContentsProvider() {
        return contentsProvider;
    }

    @Override
    public LocalDateTime findContentsTime() {
        return contentsEndAt;
    }

    @Override
    public String findDescription() {
        return company;
    }

    @Override
    public Long findViewCount() {
        return viewCount;
    }

}
