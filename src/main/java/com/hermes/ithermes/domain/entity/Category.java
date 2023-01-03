package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<CrawlingContents> crawlingContentsList;

    @OneToMany(mappedBy = "category")
    private List<Service> services;

}
