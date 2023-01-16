package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.infrastructure.ServiceRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Component
@RequiredArgsConstructor
public class ServiceFactory {
    private final ServiceRepository serviceRepository;

    public List<Service> findAllService() {
        return serviceRepository.findAll();
    }
}
