package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.util.ActiveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<List<Subscribe>> findByUserId(@Param("userId") Long userId);
    Optional<Subscribe> findByContentsProviderId(@Param("contentsProviderId") Long contentsProviderId);

    @Query("select s.contentsProvider from Subscribe s where s.isActive = :activeType and s.user.id = :userId")
    List<ContentsProvider> findContentsProvider(@Param("active")ActiveType activeType, @Param("userId") Long userId);
}
