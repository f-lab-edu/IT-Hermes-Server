package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
public class Job extends BaseEntity implements ContentsEntityInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentsProviderId")
    ContentsProvider contentsProvider;

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

    public void setContentsProvider(ContentsProvider contentsProvider) {
        this.contentsProvider = contentsProvider;
    }

    public void initDefaultData() {
        viewCount=0L;
    }
}
