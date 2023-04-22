package com.hermes.ithermes.infrastructure.elastic;

import com.hermes.ithermes.domain.entity.AlarmSearch;
import com.hermes.ithermes.domain.util.CategoryType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmSearchRepository extends ElasticsearchRepository<AlarmSearch,Long> {

    Optional<List<AlarmSearch>> findByCategory(CategoryType categoryType);
}
