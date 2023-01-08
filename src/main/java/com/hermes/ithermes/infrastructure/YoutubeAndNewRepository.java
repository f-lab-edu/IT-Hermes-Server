package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeAndNewRepository extends JpaRepository<YoutubeAndNews,Long> {

    @Query(value = "select c.title, c.image,c.url,s.category,s.name,c.contents_date,c.view_count from youtube_and_news c inner join service s order by c.view_count desc limit 10",nativeQuery = true)
    List<MainContents> findAllYoutubeAndNews();

    @Query(value = "select c from YoutubeAndNews c join fetch c.service where c.service.name = :type")
    List<YoutubeAndNews> findAllByService(@Param("type") String type);

    public static interface MainContents{

        String getTitle();

        String getImage();

        String getUrl();

        String getCategory();

        String getName();

        String getContents_Date();

        Long getView_Count();

    }

}
