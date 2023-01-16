package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.util.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByName(@Param("name") ServiceType serviceType);
}
