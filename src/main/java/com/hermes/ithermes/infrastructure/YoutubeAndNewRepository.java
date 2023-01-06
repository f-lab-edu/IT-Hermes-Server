package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeAndNewRepository extends JpaRepository<YoutubeAndNews,Long> {

    @Query(value = "select c from YoutubeAndNews c join fetch c.service order by c.viewCount desc")
    List<YoutubeAndNews> findAllYoutubeAndNews();

}
