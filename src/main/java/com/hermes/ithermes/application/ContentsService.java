package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ContentsType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.ContentsDtoInterface;
import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

    private final YoutubeAndNewsJpaRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;

    public List<ContentsDtoInterface> getMainContents(ContentsType type){
        Pageable pageInfo = PageRequest.of(0,10,Sort.by(OrderType.POPULAR.getOrderQuery()).descending());
        if(type.getName().equals("JOB")){
            return convertEntityToDtoList(jobRepository.findJobBySorting(pageInfo,type,OrderType.POPULAR), new MainPageContentsDto());
        }
        return pageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
    }

    public List<ContentsDtoInterface> getCategoryContents(ContentsType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,12, Sort.by(order.getOrderQuery()).descending());
        if(type.getName().equals("JOB")) {
            return convertEntityToDtoList(jobRepository.findJobBySorting(pageInfo,ContentsType.JOB,order), new ContentsDto());
        }
        return convertEntityToDtoList(youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageInfo,type).getContent(),new ContentsDto());
    }

    private List<ContentsDtoInterface> pageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, ContentsType type){
        Page<ContentsEntityInterface> youtubeAndNewsContents;
        if(type.equals(ContentsType.YOUTUBE_AND_NEWS)){
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsBy(page);
        }else{
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(page,type);
        }
        return convertEntityToDtoList(youtubeAndNewsContents.getContent(), new MainPageContentsDto());
    }

    private List<ContentsDtoInterface> convertEntityToDtoList(List<ContentsEntityInterface> content, ContentsDtoInterface t){
        return content.stream().map(x->t.convertEntityToDto(x)).collect(Collectors.toList());
    }

}
