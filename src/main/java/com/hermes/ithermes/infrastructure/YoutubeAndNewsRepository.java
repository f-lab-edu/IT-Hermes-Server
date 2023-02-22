package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
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

    Page<ContentsEntityInterface> findYoutubeAndNewsBy(Pageable pageable);
    Page<ContentsEntityInterface> findYoutubeAndNewsByCategory(Pageable pageable, CategoryType type);
    List<YoutubeAndNews> findFirst1ByContentsProviderOrderByUrlDesc(@Param("contentsProvider") ContentsProviderType contentsProvider);
    List<YoutubeAndNews> findYoutubeAndNewsByContentsProvider(ContentsProviderType contentsProvider);
    YoutubeAndNews findYoutubeAndNewsByUrl(String url);
    Optional<YoutubeAndNews> findByUrl(@Param("url") String url);
    List<YoutubeAndNews> findYoutubeAndNewsByCategory(CategoryType categoryType);
    List<ContentsEntityInterface> findByTitleContaining(String title);

}
