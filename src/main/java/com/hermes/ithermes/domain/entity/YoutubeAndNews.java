package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class YoutubeAndNews extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YoutubeAndNewsId")
    private Long id;

    private String title;

    private String description;

    private String image;

    private String url;

    private Boolean isDelete;

    private String contentsDate;

    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceId")
    private Service service;

}
