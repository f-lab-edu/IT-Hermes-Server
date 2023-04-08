package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Where(clause = "isDelete = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType job;

    @Column(nullable = false)
    private Integer yearOfExperience;

    private String telegramId;

    @Column(nullable = false)
    private Boolean isDelete;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void isDelete() {
        this.isDelete = true;
    }
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    List<Subscribe> subscribes;

    public void setSubscribes(List<Subscribe> subscribes) {
        this.subscribes = subscribes;
    }

    public void updateTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }
}
