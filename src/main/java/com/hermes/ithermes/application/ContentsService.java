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
            return pageJobConvertMainPageContentsDto((Page<Job>) jobRepository.findJobBySorting(pageInfo,type,OrderType.RECENT));
        }
        return pageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
    }

    public List<DtoInterface> getCategoryContents(CategoryType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,12);
        if(type.getName().equals("JOB")) {
            return pageJobToConvertContentsDto(pageInfo,order);
        }
        return pageYoutubeAndNewsConvertContentsDto(pageInfo,order,type);
    }

    private List<DtoInterface> pageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType category){
        List<YoutubeAndNews> youtubeAndNewsContents=youtubeAndNewsRepository.findYoutubeAndNewsBySorting(page,category, OrderType.valueOf(""));
        return convertEntityToDtoList(youtubeAndNewsContents,new MainPageContentsDto());
    }

    private List<DtoInterface> pageJobConvertMainPageContentsDto(Page<Job> pageJob){
        return convertEntityToDtoList(pageJob.getContent(),new MainPageContentsDto());
    }

    private List<DtoInterface> pageJobToConvertContentsDto(Pageable page,OrderType order){
        List<Job> jobContents= jobRepository.findJobBySorting(page,CategoryType.JOB,order);
        return convertEntityToDtoList(jobContents,new ContentsDto());
    }

    private List<DtoInterface> pageYoutubeAndNewsConvertContentsDto(Pageable page, OrderType order, CategoryType type){
        List<YoutubeAndNews> youtubeAndNewsContents= youtubeAndNewsRepository.findYoutubeAndNewsBySorting(page,type,order);
        return convertEntityToDtoList(youtubeAndNewsContents,new ContentsDto());
    }

    private <Y extends EntityInterface,T extends DtoInterface> List<DtoInterface> convertEntityToDtoList(List<Y> content, T t){
        return content.stream().map(x->t.convertEntity(x)).collect(Collectors.toList());
    }

}
