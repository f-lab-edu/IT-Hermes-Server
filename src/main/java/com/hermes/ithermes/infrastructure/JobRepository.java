package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    @Query(value = "select j from Job j join fetch j.service")
    List<Job> findAllJob();

}
