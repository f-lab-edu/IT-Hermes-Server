package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YoutubeAndNewsJpaRepository extends JpaRepository<YoutubeAndNews, Long> {

    @Override
    void deleteAll();
    List<YoutubeAndNews> findYoutubeAndNewsByContentsProvider(ContentsProvider contentsProvider);

}
