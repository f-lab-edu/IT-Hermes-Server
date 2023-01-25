package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Where(clause = "isDelete = false")
public class User extends BaseEntity implements UserDetails{

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

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void isDelete() {
        this.isDelete = true;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNotExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void parsePassword(String password) {
        this.password = password;
    }
}
