package com.hermes.ithermes.presentation.config;

import com.hermes.ithermes.infrastructure.elastic.JobSearchRepository;
import com.hermes.ithermes.infrastructure.elastic.YoutubeAndNewsSearchRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = {YoutubeAndNewsSearchRepository.class, JobSearchRepository.class})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticSearch.ipAddress}")
    private String ipAddress;
    @Value("${spring.elasticSearch.port}")
    private String port;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(ipAddress + ":" + port)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}