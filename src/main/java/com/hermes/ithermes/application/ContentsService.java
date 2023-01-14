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
import org.springframework.data.domain.Page;
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
            return pageJobConvertMainPageContentsDto(jobRepository.findJobByCategoryorderByViewCount(pageInfo,type));
        }else{
            return pageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
       }
    }

    public List<DtoInterface> getCategoryContents(CategoryType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,12);
        if(type.getName().equals("JOB")) {
            return pageJobToConvertContentsDto(pageInfo,order);
        }else{
            return pageYoutubeAndNewsConvertContentsDto(pageInfo,order,type);
        }
    }

    private List<DtoInterface> pageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType category){
        List<YoutubeAndNews> youtubeAndNewsContents=new ArrayList<>();
        if(category.getName().equals("YOUTUBE_AND_NEWS")){
            youtubeAndNewsContents=youtubeAndNewsRepository.findTop10YoutubeAndNews(page).getContent();
        }else{
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(page,category).getContent();
        }
        return convertEntityToDtoList(youtubeAndNewsContents,new MainPageContentsDto());
    }

    private List<DtoInterface> pageJobConvertMainPageContentsDto(Page<Job> pageJob){
        return convertEntityToDtoList(pageJob.getContent(),new MainPageContentsDto());
    }

    private List<DtoInterface> pageJobToConvertContentsDto(Pageable page,OrderType order){
        List<Job> jobContents=new ArrayList<>();
        if(order.getName().equals("RECENT")){
            jobContents=jobRepository.findJobByCategoryorderByCreatedAt(page,CategoryType.JOB).getContent();
        }else if(order.getName().equals("POPULAR")){
            jobContents=jobRepository.findJobByCategoryorderByViewCount(page, CategoryType.JOB).getContent();
        }else{
            jobContents= jobRepository.findJobByCategory(page,CategoryType.JOB).getContent();
        }
        return convertEntityToDtoList(jobContents,new ContentsDto());
    }

    private List<DtoInterface> pageYoutubeAndNewsConvertContentsDto(Pageable page, OrderType order, CategoryType type){
        List<YoutubeAndNews> youtubeAndNewsContents=new ArrayList<>();
        if(order.getName().equals("RECENT")) {
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByCreatedAt(page,type).getContent();
        }else if(order.getName().equals("POPULAR")){
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(page,type).getContent();
        }else{
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategory(page,type).getContent();
        }
        return convertEntityToDtoList(youtubeAndNewsContents,new ContentsDto());
    }

    private <Y extends EntityInterface,T extends DtoInterface> List<DtoInterface> convertEntityToDtoList(List<Y> content, T t){
        return content.stream().map(x->t.convertEntity(x)).collect(Collectors.toList());
    }


}
