package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.MainContentsDto;
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

    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;

    public List<MainContentsDto> getMainContents(){
        Page<YoutubeAndNews> contents= youtubeAndNewsRepository.findTop10YoutubeAndNews(PageRequest.of(0,10,Sort.by("viewCount").descending()));

        return contents.getContent().stream()
                .map(m->MainContentsDto.ContentsEntityToDto(m))
                .collect(Collectors.toList());
    }


    public List<ContentsDto> getCategoryContents(String type,int page){
        Pageable pageSortedByCreatedAt = PageRequest.of(page,12,Sort.by("createdAt").descending());
        if(type.equals("job")){
            Page<Job> result=jobRepository.findJobByCategory(pageSortedByCreatedAt,"job");
            List<Job> jobcontents = result.getContent();
            return jobcontents.stream()
                    .map(m->ContentsDto.JobToContentsDto(m))
                    .collect(Collectors.toList());
        }else if(type.equals("news")){
            Page<YoutubeAndNews> result = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageSortedByCreatedAt,"news");
            List<YoutubeAndNews> newsContents = result.getContent();
            return newsContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }else{
            Page<YoutubeAndNews> result = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageSortedByCreatedAt,"youtube");
            List<YoutubeAndNews> youtubeContents=result.getContent();
            return youtubeContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }
    }

}
