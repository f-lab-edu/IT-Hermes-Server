package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YoutubeAndNewsRepository {

    private final EntityManager em;

    @Autowired
    public YoutubeAndNewsRepository(EntityManager em) {
        this.em = em;
    }

    public void save(YoutubeAndNews youtubeAndNews){
        em.persist(youtubeAndNews);
    }

    public List<YoutubeAndNews> findAllYoutubeAndNews(){
        return em.createQuery("select c from YoutubeAndNews c join fetch c.service order by c.viewCount desc",YoutubeAndNews.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
