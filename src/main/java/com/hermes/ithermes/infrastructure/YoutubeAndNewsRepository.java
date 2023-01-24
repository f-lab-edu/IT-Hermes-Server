package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ContentsType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;
import java.util.ArrayList;
import java.util.List;

@Repository
public class YoutubeAndNewsRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(YoutubeAndNews youtubeAndNews){
        em.persist(youtubeAndNews);
    }

    public List<YoutubeAndNewsAlarmDto> getYoutubeAndNewsAlarmContents(Long userId){
        String jqpl="SELECT new com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto(yn.title,yn.description,yn.image,yn.url,yn.contentsStartAt,yn.contentsProvider.name) FROM Subscribe s "+
            "INNER JOIN s.contentsProvider con on con.id=s.contentsProvider.id "+
            "INNER JOIN YoutubeAndNews yn on yn.contentsProvider.id=con.id "+
                "where s.user.id=:userId";

        TypedQuery<YoutubeAndNewsAlarmDto> query=em.createQuery(jqpl,YoutubeAndNewsAlarmDto.class);
        List<YoutubeAndNewsAlarmDto> youtubeAndNewsAlarmDtoList=query
                .setParameter("userId",userId)
                .getResultList();

        return youtubeAndNewsAlarmDtoList;
    }


}
