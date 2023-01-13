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
        if(type.equals("JOB")){
            return PageJobConvertMainPageContentsDto(jobRepository.findJobByCategoryorderByViewCount(pageInfo,type));
        }else{
            return PageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
       }
    }

    public List<ContentsDto> getCategoryContents(CategoryType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,12);
        if(type.equals("JOB")) {
            return PageJobToConvertContentsDto(pageInfo,order);
        }else{
            return PageYoutubeAndNewsConvertContentsDto(pageInfo,order,type);
        }
    }

    public List<MainPageContentsDto> PageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType category){
        Page<YoutubeAndNews> youtubeAndNewsContents= category.equals("YOUTUBEANDNEWS")?
                youtubeAndNewsRepository.findTop10YoutubeAndNews(page):youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(page,category);
        return youtubeAndNewsContents.getContent().stream()
                .map(m->MainPageContentsDto.YoutubeAndNewsEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<MainPageContentsDto> PageJobConvertMainPageContentsDto(Page<Job> pageJob){
        return pageJob.getContent().stream()
                .map(m->MainPageContentsDto.JobEntityToDto(m))
                .collect(Collectors.toList());
    }


    public List<ContentsDto> PageJobToConvertContentsDto(Pageable page,OrderType order){
        if(order.equals("RECENT")){
            return jobRepository.findJobByCategoryorderByCreatedAt(page,CategoryType.JOB).getContent().stream().map(m->ContentsDto.JobToContentsDto(m)).collect(Collectors.toList());
        }else if(order.equals("POPULAR")){
            return jobRepository.findJobByCategoryorderByViewCount(page, CategoryType.JOB).getContent().stream().map(m->ContentsDto.JobToContentsDto(m)).collect(Collectors.toList());
        }else{
            return jobRepository.findJobByCategory(page,CategoryType.JOB).getContent().stream().map(m->ContentsDto.JobToContentsDto(m)).collect(Collectors.toList());
        }
    }

    public List<ContentsDto> PageYoutubeAndNewsConvertContentsDto(Pageable page, OrderType order, CategoryType type){
        if(order.equals("RECENT")) {
            return youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByCreatedAt(page,type).stream().map(m->ContentsDto.YoutubeAndNewsToContentsDto(m)).collect(Collectors.toList());
        }else if(order.equals("POPULAR")){
            return youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(page,type).stream().map(m->ContentsDto.YoutubeAndNewsToContentsDto(m)).collect(Collectors.toList());
        }else{
            return youtubeAndNewsRepository.findYoutubeAndNewsByCategory(page,type).getContent().stream().map(m->ContentsDto.YoutubeAndNewsToContentsDto(m)).collect(Collectors.toList());
        }
    }


}
