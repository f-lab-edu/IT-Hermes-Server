package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Job extends BaseEntity implements ContentsEntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobId")
    private Long id;

    private String title;

    private String url;

    private String location;

    private String company;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private Boolean isDelete;

    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;

}
