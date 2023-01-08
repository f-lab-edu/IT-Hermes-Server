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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final JobRepository jobRepository;

    public List<MainContentsDto> getMainContents(){
        List<YoutubeAndNews> contents=youtubeAndNewsRepository.findAllYoutubeAndNews();

        return contents.stream()
                .map(m->MainContentsDto.ContentsEntityToDto(m))
                .collect(Collectors.toList());
    }

    /*
    public List<ContentsDto> getCategoryContents(String type,int page){
        if(type.equals("job")){
            Pageable sortedByCreatedAt = PageRequest.of(page,12,Sort.by("createdAt").descending());
            Page<Job> result=jobRepository.findAllJob(sortedByCreatedAt);
            List<Job> jobcontents = result.getContent();
            return jobcontents.stream()
                    .map(m->ContentsDto.JobToContentsDto(m))
                    .collect(Collectors.toList());
        }else if(type.equals("news")){
            Pageable sortedByCreatedAt = PageRequest.of(page,12,Sort.by("createdAt").descending());
            Page<YoutubeAndNews> result = youtubeAndNewsRepository.findAllByService("news",sortedByCreatedAt);
            List<YoutubeAndNews> newsContents = result.getContent();
            return newsContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }else{
            Pageable sortedByCreatedAt = PageRequest.of(page,12,Sort.by("createdAt").descending());
            Page<YoutubeAndNews> result = youtubeAndNewsRepository.findAllByService("youtube",sortedByCreatedAt);
            List<YoutubeAndNews> youtubeContents=result.getContent();
            return youtubeContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }
    }*/

}
