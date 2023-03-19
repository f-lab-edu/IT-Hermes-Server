package com.hermes.ithermes;

import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.data.elasticsearch.core.SearchOperations;

public interface ElasticsearchOperations extends DocumentOperations, SearchOperations {
}
