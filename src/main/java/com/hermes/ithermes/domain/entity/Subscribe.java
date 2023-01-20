package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Subscribe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contentsProviderId")
    private ContentsProvider contentsProvider;

    private Integer minYearOfExperience;

    private Integer maxYearOfExperience;

    @Enumerated(EnumType.STRING)
    private JobType job;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActiveType isActive;

    public void setUser(User user) {
        this.user = user;
    }

    public void setContentsProvider(ContentsProvider contentsProvider) {
        this.contentsProvider = contentsProvider;
    }

    public void changeUpdateAt(Subscribe subscribe) {
        this.isActive=subscribe.getIsActive();
        if(subscribe.getJob()!=null) this.job=subscribe.getJob();
        if(subscribe.getMinYearOfExperience()!=null) this.minYearOfExperience=subscribe.getMinYearOfExperience();
        if(subscribe.getMaxYearOfExperience()!=null) this.maxYearOfExperience=subscribe.getMaxYearOfExperience();

        changeUpdateAt();
    }
}
