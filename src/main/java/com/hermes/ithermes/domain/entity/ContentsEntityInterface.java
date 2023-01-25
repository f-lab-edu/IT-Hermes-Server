package com.hermes.ithermes.domain.entity;

import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;

import java.time.LocalDateTime;


public interface ContentsEntityInterface {

    String title();
    String image();
    String url();
    CategoryType categoryType();
    ContentsProviderType contentsProvider();
    LocalDateTime contentsTime();
    String description();

}
