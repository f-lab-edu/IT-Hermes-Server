package com.hermes.ithermes.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermes.ithermes.domain.entity.CrawlingContents;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.exception.JsonParseException;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ElasticSearchType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.infrastructure.elastic.JobSearchRepository;
import com.hermes.ithermes.infrastructure.elastic.YoutubeAndNewsSearchRepository;
import com.hermes.ithermes.presentation.dto.contents.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsService {

    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;
    private final RedisTemplate<String, Object> cacheRedisTemplate;
    private final ObjectMapper objectMapper;
    private final YoutubeAndNewsSearchRepository youtubeAndNewsSearchRepository;
    private final JobSearchRepository jobSearchRepository;

    @Cacheable("top12ContentsCache")
    public List<ContentsDtoInterface> getMainContents(CategoryType type) {
        Pageable pageInfo = PageRequest.of(0, 12, Sort.by(OrderType.POPULAR.getOrderQuery()).descending());
        if (type.getTitle().equals("JOB")) {
            return convertEntityToDtoList(jobRepository.findJobBy(pageInfo).getContent(), new MainPageContentsDto());
        }
        ListOperations<String, Object> stringListValueOperations = cacheRedisTemplate.opsForList();
        if (stringListValueOperations.size("top12") == 0) {
            cacheRedisTemplate.expire("top12", 60 * 60 * 3, TimeUnit.SECONDS);
            List<ContentsDtoInterface> contentsDtoInterfaces = pageYoutubeAndNewsConvertMainPageContentsDto(pageInfo, type);
            stringListValueOperations.rightPushAll("top12", contentsDtoInterfaces);
            return contentsDtoInterfaces;
        }
        Object range = stringListValueOperations.index("top12", 0L);
        try {
            String writeValueAsString = objectMapper.writeValueAsString(range);
            List<ContentsDtoInterface> contentsDtoInterfaceList = new ArrayList<>();
            List<MainPageContentsDto> mainPageContentsDtoList = objectMapper.readValue(writeValueAsString, new TypeReference<>() {
            });

            mainPageContentsDtoList.stream().forEach(v -> contentsDtoInterfaceList.add(v));
            return contentsDtoInterfaceList;
        } catch (JsonProcessingException e) {
            throw new JsonParseException();
        }
    }

    public List<ContentsDtoInterface> getCategoryContents(CategoryType type, int page, OrderType order) {
        Pageable pageInfo = PageRequest.of(page, 12, Sort.by(order.getOrderQuery()).descending());

        if (type.getTitle().equals("JOB")) {
            return convertEntityToDtoList(jobRepository.findJobBy(pageInfo).getContent(), new ContentsDto());
        }

        return convertEntityToDtoList(youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageInfo, type).getContent(), new ContentsDto());
    }

    private List<ContentsDtoInterface> pageYoutubeAndNewsConvertMainPageContentsDto(Pageable page, CategoryType type) {
        Page<CrawlingContents> youtubeAndNewsContents;

        if (type.equals(CategoryType.YOUTUBE_AND_NEWS)) {
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsBy(page);
        } else {
            youtubeAndNewsContents = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(page, type);
        }

        return convertEntityToDtoList(youtubeAndNewsContents.getContent(), new MainPageContentsDto());
    }

    private List<ContentsDtoInterface> convertEntityToDtoList(List<CrawlingContents> content, ContentsDtoInterface t) {
        return content.stream().map(x -> t.convertEntityToDto(x)).collect(Collectors.toList());
    }

    public CategoryCountDto getCategoryCount() {
        Long youtubeCnt = youtubeAndNewsRepository.countYoutubeAndNewsByCategory(CategoryType.YOUTUBE);
        Long jobCnt = jobRepository.countBy();
        Long newsCnt = youtubeAndNewsRepository.countYoutubeAndNewsByCategory(CategoryType.NEWS);

        return new CategoryCountDto(youtubeCnt, jobCnt, newsCnt);
    }

    public SearchContentsDto getSearchContents(String title, CategoryType categoryType) {
        if (categoryType == CategoryType.JOB) {
            List<CrawlingContents> jobSearchContents = jobSearchRepository.findByTitleContaining(title);
            return new SearchContentsDto(jobSearchContents.size(),convertEntityToDtoList(jobSearchContents, new ContentsDto()));
        } else {
            List<CrawlingContents> youtubeSearchContents = youtubeAndNewsSearchRepository.findByTitleContainingAndCategory(title,categoryType);
            return new SearchContentsDto(youtubeSearchContents.size(),convertEntityToDtoList(youtubeSearchContents, new ContentsDto()));
        }
    }

    @CacheEvict(value = "top12ContentsCache", allEntries = true)
    public void deleteContentsCache() {
    }

    public void updateElasticsearch(){
        List<YoutubeAndNews> crawlingContents = youtubeAndNewsRepository.findByElasticSearchType(ElasticSearchType.READY);
        crawlingContents.stream()
                .forEach(v -> {
                    v.updateElasticSearchType();
                    youtubeAndNewsSearchRepository.save(YoutubeAndNews.convertESentity(v));
                });
        }


}
