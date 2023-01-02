package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Service extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId")
    private Long id;

    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "alarmId")
    private Alarm alarm;

}
