package com.hermes.ithermes;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

public abstract class AbstractElasticsearchConfiguration extends ElasticsearchConfigurationSupport {

    @Bean
    public abstract RestHighLevelClient elasticsearchClient();

    @Bean(name = {"elasticsearchOperations","elasticsearchTemplate"})
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter,
                                                           RestHighLevelClient elasticsearchClient){
        ElasticsearchRestTemplate template = new ElasticsearchRestTemplate(elasticsearchClient,elasticsearchConverter);
        template.setRefreshPolicy(refreshPolicy());

        return (ElasticsearchOperations) template;
    }
}
