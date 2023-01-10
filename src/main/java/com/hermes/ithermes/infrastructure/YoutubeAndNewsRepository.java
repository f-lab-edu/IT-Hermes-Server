package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeAndNewsRepository extends JpaRepository<YoutubeAndNews,Long> {

    @Query("select c from YoutubeAndNews c left join c.service where c.service.category=:category and c.isDelete=false")
    Page<YoutubeAndNews> findYoutubeAndNewsByCategory(Pageable pageable,String category);

    @Query("select c from YoutubeAndNews c left join c.service where c.service.category=:category and c.isDelete=false order by c.viewCount desc")
    Page<YoutubeAndNews> findYoutubeAndNewsByCategoryOrderByViewCount(Pageable pageable,String category);

    @Query("select c from YoutubeAndNews c left join c.service where c.service.category=:category and c.isDelete=false order by c.createdAt desc")
    Page<YoutubeAndNews> findYoutubeAndNewsByCategoryOrderByCreatedAt(Pageable pageable,String category);

    @Query("select c from YoutubeAndNews c left join c.service where c.isDelete=false")
    Page<YoutubeAndNews> findTop10YoutubeAndNews(Pageable pageable);

}
