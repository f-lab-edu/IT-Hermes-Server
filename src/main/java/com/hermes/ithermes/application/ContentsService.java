package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
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

    public List<MainPageContentsDto> getMainContents(CategoryType type){
        Pageable pageInfo = PageRequest.of(0,10);
        if(type.getName().equals("JOB")){
            return PageJobConvertMainPageContentsDto(jobRepository.findJobByCategoryorderByViewCount(pageInfo,type));
        }else{
            return PageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
       }
    }

    public List<ContentsDto> getCategoryContents(CategoryType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,12);
        if(type.getName().equals("JOB")) {
            return PageJobToConvertContentsDto(pageInfo,order);
        }else{
            return PageYoutubeAndNewsConvertContentsDto(pageInfo,order,type);
        }
    }

    public List<MainPageContentsDto> PageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType category){
        List<YoutubeAndNews> youtubeAndNewsContents=new ArrayList<>();
        if(category.getName().equals("YOUTUBE_AND_NEWS")){
            youtubeAndNewsContents=youtubeAndNewsRepository.findTop10YoutubeAndNews(page).getContent();
        }else{
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(page,category).getContent();
        }
        return youtubeAndNewsContents.stream()
                .map(m->MainPageContentsDto.YoutubeAndNewsEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<MainPageContentsDto> PageJobConvertMainPageContentsDto(Page<Job> pageJob){
        return pageJob.getContent().stream()
                .map(m->MainPageContentsDto.JobEntityToDto(m))
                .collect(Collectors.toList());
    }


    public List<ContentsDto> PageJobToConvertContentsDto(Pageable page,OrderType order){
        List<Job> jobContents=new ArrayList<>();
        if(order.getName().equals("RECENT")){
            jobContents=jobRepository.findJobByCategoryorderByCreatedAt(page,CategoryType.JOB).getContent();
        }else if(order.getName().equals("POPULAR")){
            jobContents=jobRepository.findJobByCategoryorderByViewCount(page, CategoryType.JOB).getContent();
        }else{
            jobContents= jobRepository.findJobByCategory(page,CategoryType.JOB).getContent();
        }
        return jobContents.stream()
                .map(m->ContentsDto.JobToContentsDto(m))
                .collect(Collectors.toList());
    }

    public List<ContentsDto> PageYoutubeAndNewsConvertContentsDto(Pageable page, OrderType order, CategoryType type){
        List<YoutubeAndNews> youtubeAndNewsContents=new ArrayList<>();
        if(order.getName().equals("RECENT")) {
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByCreatedAt(page,type).getContent();
        }else if(order.getName().equals("POPULAR")){
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(page,type).getContent();
        }else{
            youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategory(page,type).getContent();
        }
        return youtubeAndNewsContents.stream()
                .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                .collect(Collectors.toList());
    }


}
