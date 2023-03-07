package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YoutubeAndNewsRepository extends JpaRepository<YoutubeAndNews, Long> {

    Page<CrawlingContents> findYoutubeAndNewsBy(Pageable pageable);
    Page<CrawlingContents> findYoutubeAndNewsByCategory(Pageable pageable, CategoryType type);
    Page<CrawlingContents> findByTitleContaining(Pageable pageable,CategoryType type,String searchKeyword);
    List<YoutubeAndNews> findByUrlGreaterThanAndContentsProvider(String url,ContentsProviderType contentsProviderType);
    List<YoutubeAndNews> findYoutubeAndNewsByContentsProvider(ContentsProviderType contentsProvider);
    Optional<YoutubeAndNews> findByUrl(@Param("url") String url);
    Long countYoutubeAndNewsByCategory(@Param("category") CategoryType category);

}
