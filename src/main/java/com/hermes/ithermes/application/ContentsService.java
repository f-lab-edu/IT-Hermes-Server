package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.infrastructure.YoutubeAndNewRepository;
import com.hermes.ithermes.presentation.dto.contents.MainContentsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentsService {

    private final YoutubeAndNewRepository youtubeAndNewRepository;

    @Autowired
    public ContentsService(YoutubeAndNewRepository youtubeAndNewRepository) {
        this.youtubeAndNewRepository = youtubeAndNewRepository;
    }

    public List<MainContentsDto> getMainContents(){
        List<YoutubeAndNews> contents = youtubeAndNewRepository.findAllYoutubeAndNews();
        return contents.stream()
                .map(m-> MainContentsDto.ContentsEntityToDto(m))
                .collect(Collectors.toList());
    }

}
