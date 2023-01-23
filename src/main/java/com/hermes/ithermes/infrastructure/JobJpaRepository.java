package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobJpaRepository extends JpaRepository<Job,Long> {
    List<Job> findJobByContentsProvider(ContentsProviderType contentsProviderType);
}
