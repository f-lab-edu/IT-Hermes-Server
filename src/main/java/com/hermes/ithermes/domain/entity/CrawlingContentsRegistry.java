package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CrawlingContentsRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "keywordId")
    private Keyword keyword;

    @ManyToOne
    @JoinColumn(name = "crawlingContentsId")
    private CrawlingContents crawlingContents;

}
