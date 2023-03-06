package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;

import java.time.LocalDateTime;


public interface CrawlingContents {

    String findTitle();
    String findImage();
    String findUrl();
    CategoryType findCategoryType();
    ContentsProviderType findContentsProvider();
    LocalDateTime findContentsTime();
    String findDescription();
    Long findViewCount();

}
