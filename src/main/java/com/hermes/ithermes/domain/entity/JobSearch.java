package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import com.hermes.ithermes.domain.util.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.List;

@Entity
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

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime contentsStartAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
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
