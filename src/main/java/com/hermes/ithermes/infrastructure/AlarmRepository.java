package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<List<Alarm>> findByUserId(@Param("USER_ID") Long userId);
    Optional<Alarm> findByServiceId(@Param("SERVICE_ID") Long serviceId);
}
