package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsCrawlingDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsInsertDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsLastUrlRequestDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsLastUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class YoutubeAndNewsFactory {
    private final YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository;

    public List<YoutubeAndNews> insertYoutubeAndNews(YoutubeAndNewsInsertDto youtubeAndNewsCrawlingDtoList) {
        List<YoutubeAndNews> youtubeAndNewsList = new ArrayList<>();
        ArrayList<YoutubeAndNewsCrawlingDto> crawlingList = youtubeAndNewsCrawlingDtoList.getYoutubeAndNewsCrawlingDtoList();
        CategoryType category = youtubeAndNewsCrawlingDtoList.getCategory();
        ContentsProviderType contentsProvider = youtubeAndNewsCrawlingDtoList.getContentsProvider();
        crawlingList.stream().forEach(v-> {
            String title = v.getTitle();
            String description = v.getDescription();
            String thumbnail = v.getThumbnail();
            String url = v.getUrl();
            String[] dateArray = v.getDate().split("-");
            LocalDateTime date = LocalDateTime.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]),Integer.parseInt(dateArray[2])
                    ,Integer.parseInt(dateArray[3]),Integer.parseInt(dateArray[4]),Integer.parseInt(dateArray[5]));

            YoutubeAndNews youtubeAndNews = YoutubeAndNews.builder()
                    .description(description)
                    .title(title)
                    .image(thumbnail)
                    .url(url)
                    .contentsStartAt(date)
                    .isDelete(false)
                    .viewCount(0L)
                    .category(category)
                    .contentsProvider(contentsProvider)
                    .build();
            youtubeAndNewsList.add(youtubeAndNews);
        });
        return youtubeAndNewsList;
    }

    public YoutubeAndNewsLastUrlResponseDto findYoutubeAndNewsLastUrl(YoutubeAndNewsLastUrlRequestDto youtubeAndNewsLastUrlRequestDto) {
        List<YoutubeAndNews> lastYoutubeAndNews = youtubeAndNewsJpaRepository.findFirst1ByContentsProviderOrderByUrlDesc(youtubeAndNewsLastUrlRequestDto.getContentsProvider());
        if(!lastYoutubeAndNews.isEmpty()) {
            return YoutubeAndNewsLastUrlResponseDto.builder().lastUrl(lastYoutubeAndNews.get(0).getUrl()).contentsProvider(youtubeAndNewsLastUrlRequestDto.getContentsProvider()).build();
        }
        return YoutubeAndNewsLastUrlResponseDto.builder().contentsProvider(youtubeAndNewsLastUrlRequestDto.getContentsProvider()).build();
    }
}
