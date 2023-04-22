package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<List<Subscribe>> findByUserId(@Param("userId") Long userId);

    @Query("select s.contentsProvider from Subscribe s where s.isActive = :active and s.user.id = :userId and s.category = :category")
    List<ContentsProviderType> findContentsProvider(@Param("active") ActiveType active, @Param("userId") Long userId, @Param("category") CategoryType categoryType);

    boolean existsByUserId(@Param("userId") Long userId);

    Optional<Subscribe> findByContentsProvider(@Param("contentsProvider") ContentsProviderType contentsProvider);

    @Query(value = "select s from Subscribe s join fetch s.user u where u.telegramId is not null and s.isActive= :active and s.category = :category")
    List<Subscribe> findAlarmJoin(@Param("active") ActiveType active,@Param("category") CategoryType categoryType);

}
