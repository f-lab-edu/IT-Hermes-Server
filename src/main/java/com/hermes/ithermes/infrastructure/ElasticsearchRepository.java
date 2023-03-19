package com.hermes.ithermes.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.annotation.Nullable;

@NoRepositoryBean
public interface ElasticsearchRepository<T,ID> extends PagingAndSortingRepository<T,ID> {

    Page<T> searchSimilar(T entity, @Nullable String[] fields, Pageable pageable);

}
