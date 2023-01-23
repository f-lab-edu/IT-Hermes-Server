package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeAndNewsJpaRepository extends JpaRepository<YoutubeAndNews, Long> {

    List<YoutubeAndNews> findYoutubeAndNewsByContentsProvider(ContentsProviderType contentsProvider);

}
