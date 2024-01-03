package com.hermes.ithermes.infrastructure.elastic;

import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.entity.JobSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSearchRepository extends ElasticsearchRepository<JobSearch,Long> {
    List<CrawlingContents> findByTitleContaining(String title);
    void save(JobSearch jobSearch);
}
