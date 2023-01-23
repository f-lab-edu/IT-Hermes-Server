package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActiveType isActive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentsProviderType contentsProvider;

    public void setUser(User user) {
        this.user = user;
    }

    public void changeUpdateAt(Subscribe subscribe) {
        this.isActive=subscribe.getIsActive();
        changeUpdateAt();
    }
}
