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

    public List<MainContentsDto> getMainContents(String type){
        Pageable pageInfo = PageRequest.of(0,10);
        if(type.equals("youtubeAndNews")){
            Page<YoutubeAndNews> youtubeAndNewscontents= youtubeAndNewsRepository.findTop10YoutubeAndNews(pageInfo);
            return youtubeAndNewscontents.getContent().stream()
                    .map(m->MainContentsDto.YoutubeAndNewsEntityToDto(m))
                    .collect(Collectors.toList());
        }else if(type.equals("youtube")){
            Page<YoutubeAndNews> youtubeContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(pageInfo,"youtube");
            return youtubeContents.getContent().stream()
                    .map(m->MainContentsDto.YoutubeAndNewsEntityToDto(m))
                    .collect(Collectors.toList());
        }else if(type.equals("news")){
            Page<YoutubeAndNews> newsContents=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(pageInfo,"news");
            return newsContents.getContent().stream()
                    .map(m->MainContentsDto.YoutubeAndNewsEntityToDto(m))
                    .collect(Collectors.toList());
        }else{
            Page<Job> jobsContents=jobRepository.findJobByCategoryorderByViewCount(pageInfo,"job");
            return jobsContents.getContent().stream()
                    .map(m->MainContentsDto.JobEntityToDto(m))
                    .collect(Collectors.toList());
        }
    }

    public List<ContentsDto> getCategoryContents(String type,int page,String order){
        Pageable pageInfo = PageRequest.of(page,12);
        if(type.equals("job") && order==null){
            Page<Job> jobResult=jobRepository.findJobByCategory(pageInfo,"job");
            List<Job> jobcontents = jobResult.getContent();
            return jobcontents.stream()
                    .map(m->ContentsDto.JobToContentsDto(m))
                    .collect(Collectors.toList());
        }else if(type.equals("job") && order.equals("recent")){
            Page<Job> jobRecentResult=jobRepository.findJobByCategoryorderByCreatedAt(pageInfo,"job");
            List<Job> jobRecentContents=jobRecentResult.getContent();
            return jobRecentContents.stream()
                    .map(m->ContentsDto.JobToContentsDto(m))
                    .collect(Collectors.toList());
        }else if(type.equals("job") && order.equals("popular")){
            Page<Job> jobPopularResult=jobRepository.findJobByCategoryorderByViewCount(pageInfo,"job");
            List<Job> jobPopularContents=jobPopularResult.getContent();
            return jobPopularContents.stream()
                    .map(m->ContentsDto.JobToContentsDto(m))
                    .collect(Collectors.toList());
        }else if((type.equals("news") || type.equals("youtube")) && order==null){
            Page<YoutubeAndNews> youtubeAndNewsResult = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageInfo,type);
            List<YoutubeAndNews> youtubeAndNewsContents = youtubeAndNewsResult.getContent();
            return youtubeAndNewsContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }else if((type.equals("news") || type.equals("youtube")) && order.equals("recent")){
            Page<YoutubeAndNews> youtubeAndNewsRecentResult=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByCreatedAt(pageInfo,type);
            List<YoutubeAndNews> youtubeAndNewsRecentContents=youtubeAndNewsRecentResult.getContent();
            return youtubeAndNewsRecentContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }else{
            Page<YoutubeAndNews> youtubeAndNewsRecentResult=youtubeAndNewsRepository.findYoutubeAndNewsByCategoryOrderByViewCount(pageInfo,type);
            List<YoutubeAndNews> youtubeAndNewsRecentContents=youtubeAndNewsRecentResult.getContent();
            return youtubeAndNewsRecentContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }
    }

}
