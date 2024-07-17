package com.hermes.ithermes.infrastructure;

import java.util.List;

import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.ElasticSearchType;
import com.hermes.ithermes.domain.util.GradeType;
import com.hermes.ithermes.domain.util.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    Page<CrawlingContents> findJobBy(Pageable pageable);
    List<CrawlingContents> findByTitleContaining(String searchKeyword);
    @Query("select jn from Job jn where jn.id<(select innerjn.id from Job innerjn where innerjn.url=:url and innerjn.contentsProvider=:contentsProviderType and innerjn.grade=:GradeType and innerjn.jobType=:JobType) and jn.contentsProvider=:contentsProviderType and jn.grade=:GradeType and jn.jobType=:JobType")
    List<Job> findJobByUrlGreater(@Param("url") String url, @Param("contentsProviderType") ContentsProviderType contentsProvider, @Param("GradeType") GradeType gradeType, @Param("JobType")JobType jobType);
    List<Job> findJobByContentsProvider(ContentsProviderType contentsProviderType);
    Optional<List<Job>> findByUrl(@Param("url") String url);
    Long countBy();
    List<Job> findByElasticSearchType(ElasticSearchType elasticSearchType);

}
