package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    public Service(Long id, Boolean isDelete, String name, String category) {
        this.id = id;
        this.isDelete = isDelete;
        this.name = name;
        this.category = category;
    }
}
