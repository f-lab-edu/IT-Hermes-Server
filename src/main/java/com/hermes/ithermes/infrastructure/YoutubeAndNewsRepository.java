package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YoutubeAndNewsRepository extends JpaRepository<YoutubeAndNews, Long> {

    Page<CrawlingContents> findYoutubeAndNewsBy(Pageable pageable);
    Page<CrawlingContents> findYoutubeAndNewsByCategory(Pageable pageable, CategoryType type);
    List<CrawlingContents> findByTitleContainingAndCategory(String searchKeyword,CategoryType type);
    @Query("select yn from YoutubeAndNews yn where yn.id<(select inneryn.id from YoutubeAndNews inneryn where inneryn.url=:url and inneryn.contentsProvider=:contentsProviderType) and yn.contentsProvider=:contentsProviderType")
    List<YoutubeAndNews> findYoutubeAndNewsByUrlGreater(@Param("url") String url,@Param("contentsProviderType") ContentsProviderType contentsProviderType);
    List<YoutubeAndNews> findYoutubeAndNewsByContentsProvider(ContentsProviderType contentsProvider);
    Optional<List<YoutubeAndNews>> findByUrl(@Param("url") String url);
    Long countYoutubeAndNewsByCategory(@Param("category") CategoryType category);

}