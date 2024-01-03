package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRegistryRepository extends JpaRepository<UserKeywordRegistry, Long> {
}
