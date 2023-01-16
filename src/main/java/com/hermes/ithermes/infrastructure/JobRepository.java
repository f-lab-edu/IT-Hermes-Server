package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobRepository {

        @PersistenceContext
        private EntityManager em;

        public List<ContentsEntityInterface> findJobBySorting(Pageable pageable, CategoryType categoryType, OrderType orderType){
            String jpql="select j from Job j left join j.service where j.service.category=:category and j.isDelete=false";

            if(orderType.getName().equals("RECENT")){
                jpql+=" order by j.createdAt desc";
            }else if(orderType.getName().equals("POPULAR")){
                jpql+=" order by j.viewCount desc";
            }else{
                jpql+="";
            }
            TypedQuery<ContentsEntityInterface> query=em.createQuery(jpql, ContentsEntityInterface.class);

            List<ContentsEntityInterface> jobs=query.setFirstResult((int)pageable.getOffset())
                    .setParameter("category",categoryType.JOB)
                    .setMaxResults(pageable.getPageSize()+1)
                    .getResultList();

            return jobs;
        };

}
