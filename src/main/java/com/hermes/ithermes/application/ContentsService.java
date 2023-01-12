package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import com.sun.tools.javac.Main;
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

    public List<MainPageContentsDto> getMainContents(String type){
        Pageable pageInfo = PageRequest.of(0,10);
        if(type.equals("YOUTUBEANDNEWS")){
            return PageYoutubeAndNewsConvertMainPageContentsDto(youtubeAndNewsRepository.findTop10YoutubeAndNews(pageInfo));
        }else if(type.equals("YOUTUBE")){
            return PageYoutubeAndNewsConvertMainPageContentsDto(youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(pageInfo,"YOUTUBE"));
        }else if(type.equals("NEWS")){
            return PageYoutubeAndNewsConvertMainPageContentsDto(youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(pageInfo,"NEWS"));
        }else{
            return PageJobConvertMainPageContentsDto(jobRepository.findJobByCategoryorderByViewCount(pageInfo,"JOB"));
        }
    }

    public List<ContentsDto> getCategoryContents(String type,int page,String order){
        Pageable pageInfo = PageRequest.of(page,12);
        if(type.equals("JOB") && order==null){
            return PageJobToConvertContentsDto(jobRepository.findJobByCategory(pageInfo,"JOB"));
        }else if(type.equals("JOB") && order.equals("RECENT")){
            return PageJobToConvertContentsDto(jobRepository.findJobByCategoryorderByCreatedAt(pageInfo,"JOB"));
        }else if(type.equals("JOB") && order.equals("POPULAR")){
            return PageJobToConvertContentsDto(jobRepository.findJobByCategoryorderByViewCount(pageInfo,"JOB"));
        }else if((type.equals("NEWS") || type.equals("YOUTUBE")) && order==null){
            return PageYoutubeAndNewsConvertContentsDto(youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageInfo,type));
        }else if((type.equals("NEWS") || type.equals("YOUTUBE")) && order.equals("RECENT")){
            return PageYoutubeAndNewsConvertContentsDto(youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByCreatedAt(pageInfo,type));
        }else{
            return PageYoutubeAndNewsConvertContentsDto(youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(pageInfo,type));
        }
    }

    public List<MainPageContentsDto> PageYoutubeAndNewsConvertMainPageContentsDto(Page<YoutubeAndNews> pageYoutubeAndNews){
        return pageYoutubeAndNews.getContent().stream()
                .map(m->MainPageContentsDto.YoutubeAndNewsEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<MainPageContentsDto> PageJobConvertMainPageContentsDto(Page<Job> pageJob){
        return pageJob.getContent().stream()
                .map(m->MainPageContentsDto.JobEntityToDto(m))
                .collect(Collectors.toList());
    }

    public List<ContentsDto> PageJobToConvertContentsDto(Page<Job> pageJob){
        return pageJob.getContent().stream()
                .map(m->ContentsDto.JobToContentsDto(m))
                .collect(Collectors.toList());
    }

    public List<ContentsDto> PageYoutubeAndNewsConvertContentsDto(Page<YoutubeAndNews> pageYoutubeAndNews){
        return pageYoutubeAndNews.getContent().stream()
                .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                .collect(Collectors.toList());
    }


}
