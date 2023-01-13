package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.convert.Jsr310Converters;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YoutubeAndNews extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YoutubeAndNewsId")
    private Long id;

    private String title;

    private String description;

    private String image;

    private String url;

    private Boolean isDelete;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime contentsDate;

    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;

}
