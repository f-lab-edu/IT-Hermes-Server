package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Job extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobId")
    private Long id;

    private String title;

    private String url;

    private String location;

    private String company;

    private String startDate;

    private String endDate;

    private Boolean isDelete;

    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;

}
