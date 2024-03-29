package com.hermes.ithermes.infrastructure.elastic;

import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.entity.YoutubeAndNewsSearch;
import com.hermes.ithermes.domain.util.CategoryType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeAndNewsSearchRepository extends ElasticsearchRepository<YoutubeAndNewsSearch, Long> {
    List<CrawlingContents> findByTitleContainingAndCategory(String title, CategoryType categoryType);
    void save(YoutubeAndNewsSearch youtubeAndNewsSearch);
}
