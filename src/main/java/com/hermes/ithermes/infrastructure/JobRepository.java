package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    @Query(
            value = "select j from Job j left join j.service where j.service.category=:category",
            countQuery = "select count(j.title) from Job j"
    )
    Page<Job> findJobByCategory(Pageable pageable, String category);

}
