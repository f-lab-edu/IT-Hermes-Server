package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobJpaRepository extends JpaRepository<Job,Long> {

    @Override
    void deleteAll();

    List<Job> findJobByContentsProvider(ContentsProvider contentsProvider);
}
