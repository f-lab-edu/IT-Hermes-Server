package com.hermes.ithermes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        var nowTime = LocalDateTime.now();
        this.createdAt = nowTime;
        this.updatedAt = nowTime;
    }

    public void changeUpdateAt() {
        this.updatedAt = LocalDateTime.now();
    }
}