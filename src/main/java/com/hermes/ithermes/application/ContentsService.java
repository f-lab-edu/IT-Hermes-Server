package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.infrastructure.JobJpaRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.presentation.dto.contents.CategoryCountDto;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

    private final YoutubeAndNewsJpaRepository youtubeAndNewsRepository;
    private final JobJpaRepository jobRepository;

    public List<ContentsDtoInterface> getMainContents(CategoryType type){
        Pageable pageInfo = PageRequest.of(0,10,Sort.by(OrderType.POPULAR.getOrderQuery()).descending());
        if(type.getTitle().equals("JOB")){
            return convertEntityToDtoList(jobRepository.findJobBy(pageInfo).getContent(), new MainPageContentsDto());
        }
        return pageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
    }

    public List<ContentsDtoInterface> getCategoryContents(CategoryType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,8, Sort.by(order.getOrderQuery()).descending());
        if(type.getTitle().equals("JOB")) {
            return convertEntityToDtoList(jobRepository.findJobBy(pageInfo).getContent(), new ContentsDto());
        }
        return convertEntityToDtoList(youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageInfo,type).getContent(),new ContentsDto());
    }

    private List<ContentsDtoInterface> pageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType type){
        Page<ContentsEntityInterface> youtubeAndNewsContents;
        if(type.equals(CategoryType.YOUTUBE_AND_NEWS)){
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsBy(page);
        }else{
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(page,type);
        }
        return convertEntityToDtoList(youtubeAndNewsContents.getContent(), new MainPageContentsDto());
    }

    private List<ContentsDtoInterface> convertEntityToDtoList(List<ContentsEntityInterface> content, ContentsDtoInterface t){
        return content.stream().map(x->t.convertEntityToDto(x)).collect(Collectors.toList());
    }

    public CategoryCountDto getCategoryCount(){
        int youtubeCnt = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(CategoryType.YOUTUBE).size();
        int jobCnt = jobRepository.findJobBy().size();
        int newsCnt = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(CategoryType.NEWS).size();

        return new CategoryCountDto(youtubeCnt,jobCnt,newsCnt);
    }

}
