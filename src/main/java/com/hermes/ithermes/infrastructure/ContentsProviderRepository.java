package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContentsProviderRepository extends JpaRepository<ContentsProvider, Long> {
    Optional<ContentsProvider> findByName(@Param("name") ContentsProviderType contentsProviderType);
}
