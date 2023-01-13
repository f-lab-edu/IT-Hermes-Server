package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.util.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    @Query(value = "select j from Job j left join j.service where j.service.category=:category and j.isDelete=false")
    Page<Job> findJobByCategory(Pageable pageable, CategoryType category);

    @Query(value = "select j from Job j left join j.service where j.service.category=:category and j.isDelete=false order by j.viewCount desc")
    Page<Job> findJobByCategoryorderByViewCount(Pageable pageable, CategoryType category);

    @Query(value = "select j from Job j left join j.service where j.service.category=:category and j.isDelete=false order by j.createdAt desc")
    Page<Job> findJobByCategoryorderByCreatedAt(Pageable pageable, CategoryType category);

}
