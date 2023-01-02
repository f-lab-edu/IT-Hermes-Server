package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Keyword extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keywordId")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "keyword")
    private List<UserRegistry> registries;

}
