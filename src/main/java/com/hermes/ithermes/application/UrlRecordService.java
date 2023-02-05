package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.UrlRecord;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.exception.NoCrawlingDataException;
import com.hermes.ithermes.domain.factory.UrlRecordFactory;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.UrlRecordRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
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
    private final JobRepository jobRepository;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;

    @Transactional
    public CommonResponseDto putViewCount(UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto, String ipAddress) {
        String url = urlRecordPutViewCountRequestDto.getUrl();
        boolean existsView = urlRecordRepository.existsByUrlAndClientIpAddress(url, ipAddress);
        if (existsView) return new CommonResponseDto();

        UrlRecord urlRecord = urlRecordFactory.parseUrlRecord(urlRecordPutViewCountRequestDto, ipAddress);
        urlRecordRepository.save(urlRecord);
        List<Job> jobList = jobRepository.findByUrl(url).orElseThrow(() -> new NoCrawlingDataException());

        if (jobList.isEmpty()) {
            YoutubeAndNews youtubeAndNews = youtubeAndNewsRepository.findByUrl(url).orElseThrow(() -> new NoCrawlingDataException());
            youtubeAndNews.updateViewCount();
        } else {
            jobList.stream().forEach(job -> job.updateViewCount());
        }

        return new CommonResponseDto();
    }
}
