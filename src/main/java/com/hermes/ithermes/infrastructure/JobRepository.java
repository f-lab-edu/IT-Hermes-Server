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

        public List<JobAlarmDto> getJobAlarmContents(Long userId){
            String jpql="SELECT new com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto(j.title,j.location,j.company,j.url,j.contentsEndAt,j.contentsProvider.name) FROM Subscribe s " +
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
