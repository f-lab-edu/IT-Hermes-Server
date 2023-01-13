package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeAndNewsRepository extends JpaRepository<YoutubeAndNews,Long> {

    @Query("select c from YoutubeAndNews c left join c.service where c.service.category=:category and c.isDelete=false")
    Page<YoutubeAndNews> findYoutubeAndNewsByCategory(Pageable pageable,CategoryType category);

    @Query("select c from YoutubeAndNews c left join c.service where c.service.category=:category and c.isDelete=false order by c.viewCount desc")
    Page<YoutubeAndNews> findYoutubeAndNewsByCategoryOrderByViewCount(Pageable pageable, CategoryType category);

    @Query("select c from YoutubeAndNews c left join c.service where c.service.category=:category and c.isDelete=false order by c.createdAt desc")
    Page<YoutubeAndNews> findYoutubeAndNewsByCategoryOrderByCreatedAt(Pageable pageable,CategoryType category);

    @Query("select c from YoutubeAndNews c left join c.service where c.isDelete=false order by c.viewCount desc")
    Page<YoutubeAndNews> findTop10YoutubeAndNews(Pageable pageable);

}
