package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.CrawlingContentsLastUrl;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import com.hermes.ithermes.domain.util.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrawlingContentsLastUrlRepository extends JpaRepository<CrawlingContentsLastUrl, Long> {
    List<CrawlingContentsLastUrl> findAll();
    Optional<CrawlingContentsLastUrl> findByContentsProvider(@Param("contentsProvider")ContentsProviderType contentsProvider);
    Optional<CrawlingContentsLastUrl> findByContentsProviderAndGradeAndJob(@Param("contentsProvider")ContentsProviderType contentsProvider, @Param("grade")GradeType grade, @Param("job") JobType job);
}
