package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YoutubeAndNewsRepository {

    @PersistenceContext
    private EntityManager em;

    public List<ContentsEntityInterface> findTop10YoutubeAndNews(Pageable pageable){
        String jpql="select c from YoutubeAndNews c left join c.contentsProvider where c.isDelete=false order by c.viewCount desc";

        TypedQuery<ContentsEntityInterface> query= em.createQuery(jpql, ContentsEntityInterface.class);
        List<ContentsEntityInterface> youtubeAndNews=query.setFirstResult((int)pageable.getOffset())
                .setMaxResults(pageable.getPageSize()+1)
                .getResultList();
        return youtubeAndNews;
    }

    public List<ContentsEntityInterface> findYoutubeAndNewsBySorting(Pageable pageable, ContentsType contentsType, OrderType orderType){
        String jpql="select c from YoutubeAndNews c left join c.contentsProvider where c.contentsProvider.category=:contentsType and c.isDelete=false";

        if(orderType.getName().equals("RECENT")){
            jpql+=" order by c.createdAt desc";
        }else if(orderType.getName().equals("POPULAR")){
            jpql+=" order by c.viewCount desc";
        }else{
            jpql+="";
        }
        TypedQuery<ContentsEntityInterface> query= em.createQuery(jpql, ContentsEntityInterface.class);

        List<ContentsEntityInterface> youtubeAndNews=query.setFirstResult((int)pageable.getOffset())
                .setParameter("category",contentsType)
                .setMaxResults(pageable.getPageSize()+1)
                .getResultList();

        return youtubeAndNews;
    }

    public void save(YoutubeAndNews youtubeAndNews){
        em.persist(youtubeAndNews);
    }


}
