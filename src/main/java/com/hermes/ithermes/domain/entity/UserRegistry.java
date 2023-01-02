package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "userRegistry")
@Getter
public class UserRegistry extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "keywordId")
    private Keyword keyword;

}
