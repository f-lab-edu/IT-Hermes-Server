package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface YoutubeAndNewsJpaRepository extends JpaRepository<YoutubeAndNews, Long> {

    Page<ContentsEntityInterface> findYoutubeAndNewsBy(Pageable pageable);
    Page<ContentsEntityInterface> findYoutubeAndNewsByCategory(Pageable pageable, CategoryType type);

}
