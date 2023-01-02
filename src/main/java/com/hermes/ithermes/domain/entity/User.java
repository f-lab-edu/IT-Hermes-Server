package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private Integer experience;

    private String telegramId;

    @Column(nullable = false)
    private boolean isDelete;

    @OneToMany(mappedBy = "user")
    private List<UserRegistry> userRegistries;

    @OneToMany(mappedBy = "user")
    private List<Alarm> alarms;
}
