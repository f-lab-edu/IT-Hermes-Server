package com.hermes.ithermes;

import com.hermes.ithermes.infrastructure.elastic.AlarmSearchRepository;
import com.hermes.ithermes.infrastructure.elastic.JobSearchRepository;
import com.hermes.ithermes.infrastructure.elastic.YoutubeAndNewsSearchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JobSearchRepository.class, YoutubeAndNewsSearchRepository.class, AlarmSearchRepository.class}))
public class ItHermesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItHermesApplication.class, args);
    }
}


