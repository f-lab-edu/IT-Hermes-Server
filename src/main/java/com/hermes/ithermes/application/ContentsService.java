package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.DtoInterface;
import com.hermes.ithermes.presentation.dto.contents.EntityInterface;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;

    public List<DtoInterface> getMainContents(CategoryType type){
        Pageable pageInfo = PageRequest.of(0,10);
        if(type.getName().equals("JOB")){
            return convertEntityToDtoList(jobRepository.findJobBySorting(pageInfo,type,OrderType.POPULAR),new MainPageContentsDto());
        }
        return pageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
    }

    public List<DtoInterface> getCategoryContents(CategoryType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,12);
        if(type.getName().equals("JOB")) {
            return convertEntityToDtoList(jobRepository.findJobBySorting(pageInfo,CategoryType.JOB,order),new ContentsDto());
        }
        return convertEntityToDtoList(youtubeAndNewsRepository.findYoutubeAndNewsBySorting(pageInfo,type,order),new ContentsDto());
    }

    private List<DtoInterface> pageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType category){
        List<YoutubeAndNews> youtubeAndNewsContents=new ArrayList<>();
        if(category.equals(CategoryType.YOUTUBE_AND_NEWS)){
            youtubeAndNewsContents=youtubeAndNewsRepository.findTop10YoutubeAndNews(page);
        }else{
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsBySorting(page,category,OrderType.POPULAR);
        }
        return convertEntityToDtoList(youtubeAndNewsContents,new MainPageContentsDto());
    }

    private <Y extends EntityInterface,T extends DtoInterface> List<DtoInterface> convertEntityToDtoList(List<Y> content, T t){
        return content.stream().map(x->t.convertEntity(x)).collect(Collectors.toList());
    }

}
