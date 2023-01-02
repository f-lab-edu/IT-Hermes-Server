package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class CrawlingContents extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crawlingContentsId")
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

    private Boolean isDelete;

    @OneToMany(mappedBy = "crawlingContents")
    private List<CrawlingContentsRegistry> crawlingContentsRegistryList;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

}
