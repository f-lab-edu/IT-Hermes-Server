package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Alarm extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarmId")
    private Long id;

    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;
}
