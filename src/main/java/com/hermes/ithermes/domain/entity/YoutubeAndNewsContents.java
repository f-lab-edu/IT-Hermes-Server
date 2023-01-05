package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class YoutubeAndNewsContents extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YoutubeAndNewsContentsId")
    private Long id;

    private String title;

    private String description;

    private String image;

    private String url;

    private Boolean isDelete;

    private String contentsDate;

    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;

}
