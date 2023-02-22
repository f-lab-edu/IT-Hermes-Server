package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.UrlRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRecordRepository extends JpaRepository<UrlRecord, Long> {
    boolean existsByUrlAndClientIpAddress(@Param("url") String url, @Param("clientIpAddress") String clientIpAddress);
}
