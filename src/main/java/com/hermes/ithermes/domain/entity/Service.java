package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Getter
public class Service extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId")
    private Long id;

    private Boolean isDelete;

    private String name;

    private String category;

    @ManyToOne
    @JoinColumn(name = "alarmId")
    private Alarm alarm;

    @OneToMany(mappedBy = "service")
    private List<YoutubeAndNews> youtubeAndNewsContents;

}
