package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CrawlingContentsLastUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentsProviderType contentsProvider;

    @Enumerated(EnumType.STRING)
    private GradeType grade;

    @Column(nullable = false, length = 1000)
    private String lastUrl;

    public void change(CrawlingContentsLastUrl crawlingContentsLastUrl) {
        this.lastUrl = crawlingContentsLastUrl.getLastUrl();
    }
}