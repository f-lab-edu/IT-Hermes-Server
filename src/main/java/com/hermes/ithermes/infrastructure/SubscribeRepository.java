package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<List<Subscribe>> findByUserId(@Param("userId") Long userId);
    boolean existsByUserId(@Param("userId") Long userId);
}
