package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CrawlingContents extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long referenceCnt;

}
