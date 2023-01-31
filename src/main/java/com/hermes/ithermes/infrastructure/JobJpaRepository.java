package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.domain.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobJpaRepository extends JpaRepository<Job,Long> {
    Page<ContentsEntityInterface> findJobBy(Pageable pageable);
    Optional<List<Job>> findByUrl(@Param("url") String url);
    List<Job> findJobBy();
}
