package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.presentation.dto.contents.CategoryCountDto;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.ContentsDtoInterface;
import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;

    @Cacheable(value = "top12ContentsCache")
    public List<ContentsDtoInterface> getMainContents(CategoryType type){
        Pageable pageInfo = PageRequest.of(0,12,Sort.by(OrderType.POPULAR.getOrderQuery()).descending());
        
        if(type.getTitle().equals("JOB")){
            return convertEntityToDtoList(jobRepository.findJobBy(pageInfo).getContent(), new MainPageContentsDto());
        }

        return pageYoutubeAndNewsConvertMainPageContentsDto(pageInfo,type);
    }

    public List<ContentsDtoInterface> getCategoryContents(CategoryType type, int page, OrderType order){
        Pageable pageInfo = PageRequest.of(page,12, Sort.by(order.getOrderQuery()).descending());

        if(type.getTitle().equals("JOB")) {
            return convertEntityToDtoList(jobRepository.findJobBy(pageInfo).getContent(), new ContentsDto());
        }

        return convertEntityToDtoList(youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageInfo,type).getContent(),new ContentsDto());
    }

    private List<ContentsDtoInterface> pageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType type){
        Page<CrawlingContents> youtubeAndNewsContents;

        if(type.equals(CategoryType.YOUTUBE_AND_NEWS)){
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsBy(page);
        }else{
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(page,type);
        }

        return convertEntityToDtoList(youtubeAndNewsContents.getContent(), new MainPageContentsDto());
    }

    private List<ContentsDtoInterface> convertEntityToDtoList(List<CrawlingContents> content, ContentsDtoInterface t){
        return content.stream().map(x->t.convertEntityToDto(x)).collect(Collectors.toList());
    }

    public CategoryCountDto getCategoryCount(){
        Long youtubeCnt = youtubeAndNewsRepository.countYoutubeAndNewsByCategory(CategoryType.YOUTUBE);
        Long jobCnt = jobRepository.countBy();
        Long newsCnt = youtubeAndNewsRepository.countYoutubeAndNewsByCategory(CategoryType.NEWS);

        return new CategoryCountDto(youtubeCnt,jobCnt,newsCnt);
    }

    public List<ContentsDtoInterface> getSearchContents(int page,String title,CategoryType categoryType){
        Pageable pageInfo = PageRequest.of(page,12);
        if(categoryType==CategoryType.JOB){
            return convertEntityToDtoList(jobRepository.findByTitleContaining(pageInfo,categoryType,title).getContent(),new ContentsDto());
        }else{
            return convertEntityToDtoList(youtubeAndNewsRepository.findByTitleContaining(pageInfo,categoryType,title).getContent(),new ContentsDto());
        }
    }

    @CacheEvict(value = "top12ContentsCache",allEntries = true)
    public void deleteContentsCache(){
    }


}
