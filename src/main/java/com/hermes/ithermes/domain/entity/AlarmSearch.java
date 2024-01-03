package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "alarmsearch")
public class AlarmSearch {

    @Id
    private String id;

    private ContentsProviderType contentsProvider;

    private Long userId;

    private CategoryType category;

    private String telegramId;

}
