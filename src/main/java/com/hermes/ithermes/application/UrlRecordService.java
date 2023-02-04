package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.UrlRecord;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.exception.NoCrawlingDataException;
import com.hermes.ithermes.domain.factory.UrlRecordFactory;
import com.hermes.ithermes.infrastructure.JobJpaRepository;
import com.hermes.ithermes.infrastructure.UrlRecordRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.urlrecord.UrlRecordPutViewCountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlRecordService {
    private final UrlRecordRepository urlRecordRepository;
    private final UrlRecordFactory urlRecordFactory;
    private final JobJpaRepository jobJpaRepository;
    private final YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository;

    @Transactional
    public CommonResponseDto putViewCount(UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto, String ipAddress) {
        String url = urlRecordPutViewCountRequestDto.getUrl();
        boolean existsView = urlRecordRepository.existsByUrlAndClientIpAddress(url, ipAddress);
        if (existsView) return new CommonResponseDto();

        UrlRecord urlRecord = urlRecordFactory.parseUrlRecord(urlRecordPutViewCountRequestDto, ipAddress);
        urlRecordRepository.save(urlRecord);
        List<Job> jobList = jobJpaRepository.findByUrl(url).orElseThrow(() -> new NoCrawlingDataException());

        if (jobList.isEmpty()) {
            YoutubeAndNews youtubeAndNews = youtubeAndNewsJpaRepository.findByUrl(url).orElseThrow(() -> new NoCrawlingDataException());
            youtubeAndNews.updateViewCount();
        } else {
            jobList.stream().forEach(job -> job.updateViewCount());
        }

        return new CommonResponseDto();
    }
}
