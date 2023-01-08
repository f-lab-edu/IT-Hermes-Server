package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewRepository.*;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final YoutubeAndNewRepository youtubeAndNewRepository;
    private final JobRepository jobRepository;

    public List<MainContents> getMainContents(){
        return youtubeAndNewRepository.findAllYoutubeAndNews();
    }

    public List<ContentsDto> getCategoryContents(String type){
        if(type.equals("job")){
            List<Job> jobcontents=jobRepository.findAllJob();
            return jobcontents.stream()
                    .map(m->ContentsDto.JobToContentsDto(m))
                    .collect(Collectors.toList());
        }else if(type.equals("news")){
            List<YoutubeAndNews> newsContents=youtubeAndNewRepository.findAllByService("news");
            return newsContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }else{
            List<YoutubeAndNews> youtubeContents = youtubeAndNewRepository.findAllByService("youtube");
            return youtubeContents.stream()
                    .map(m->ContentsDto.YoutubeAndNewsToContentsDto(m))
                    .collect(Collectors.toList());
        }
    }

}
