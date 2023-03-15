package com.hermes.ithermes.infrastructure;

import java.util.List;

import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.GradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    Page<CrawlingContents> findJobBy(Pageable pageable);
    List<CrawlingContents> findByTitleContaining(String searchKeyword);
    List<Job> findJobByUrlGreaterThanAndContentsProviderAndGrade(String url, ContentsProviderType contentsProviderType, GradeType gradeType);
    List<Job> findJobByContentsProvider(ContentsProviderType contentsProviderType);
    Optional<List<Job>> findByUrl(@Param("url") String url);
    Long countBy();

}
