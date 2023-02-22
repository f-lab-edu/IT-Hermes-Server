package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    Page<ContentsEntityInterface> findJobBy(Pageable pageable);
    List<Job> findFirst1ByContentsProviderOrderByUrlDesc(@Param("contentsProvider") ContentsProviderType contentsProvider);
    List<Job> findJobByContentsProvider(ContentsProviderType contentsProviderType);
    Job findJobByUrl(String url);
    Optional<List<Job>> findByUrl(@Param("url") String url);
    List<Job> findJobBy();
    List<ContentsEntityInterface> findByTitleContaining(String title);

}
