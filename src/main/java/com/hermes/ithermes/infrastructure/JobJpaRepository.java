package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobJpaRepository extends JpaRepository<Job, Long> {
    List<Job> findFirst1ByContentsProviderOrderByUrlDesc(@Param("contentsProvider") ContentsProviderType contentsProvider);
}