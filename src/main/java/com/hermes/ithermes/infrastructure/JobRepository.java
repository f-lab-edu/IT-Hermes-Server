package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.util.ContentsType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
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

        public List<ContentsEntityInterface> findJobBySorting(Pageable pageable, ContentsType type, OrderType orderType){
            String jpql="select j from Job j left join j.contentsProvider where j.contentsProvider.category=:type and j.isDelete=false";

            if(orderType.getName().equals("RECENT")){
                jpql+=" order by j.createdAt desc";
            }else if(orderType.getName().equals("POPULAR")){
                jpql+=" order by j.viewCount desc";
            }else{
                jpql+="";
            }
            TypedQuery<ContentsEntityInterface> query=em.createQuery(jpql, ContentsEntityInterface.class);

            List<ContentsEntityInterface> jobs=query.setFirstResult((int)pageable.getOffset())
                    .setParameter("category",ContentsType.JOB)
                    .setMaxResults(pageable.getPageSize()+1)
                    .getResultList();

            return jobs;
        };

        public List<JobAlarmDto> getJobAlarm(Long userId){
            String jpql="SELECT new com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto(j.title,j.contentsProvider.category,j.location,j.company,j.url,j.contentsEndAt) FROM Subscribe s " +
                    "INNER JOIN s.contentsProvider con on con.id=s.contentsProvider.id " +
                    "INNER JOIN Job j on j.contentsProvider.id=con.id " +
                    "where s.user.id=:userId";

            TypedQuery<JobAlarmDto> query=em.createQuery(jpql,JobAlarmDto.class);
            List<JobAlarmDto> jobAlarmDtoList=query
                    .setParameter("userId",userId)
                    .getResultList();

            return jobAlarmDtoList;
        }



}
