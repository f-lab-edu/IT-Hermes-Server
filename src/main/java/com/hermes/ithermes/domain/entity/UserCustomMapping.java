package com.hermes.ithermes.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
/**
 * 추후 메핑작업 진행
 * */
@Entity
@Getter
public class UserCustomMapping {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn (name="user_id", referencedColumnName="id" )
    private User user;
}
