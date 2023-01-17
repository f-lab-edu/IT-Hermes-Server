package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
