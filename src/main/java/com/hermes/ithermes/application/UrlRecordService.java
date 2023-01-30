package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.UrlRecord;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.exception.NoCrawlingDataException;
import com.hermes.ithermes.domain.factory.UrlRecordFactory;
import com.hermes.ithermes.infrastructure.JobJpaRepository;
import com.hermes.ithermes.infrastructure.UrlRecordRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsJpaRepository;
import com.hermes.ithermes.presentation.dto.urlrecord.UrlRecordPutViewCountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlRecordService {
    private final UrlRecordRepository urlRecordRepository;
    private final UrlRecordFactory urlRecordFactory;
    private final JobJpaRepository jobJpaRepository;
    private final YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository;

    @Transactional
    public void putViewCount(UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto, String ipAddress) {
        String url = urlRecordPutViewCountRequestDto.getUrl();
        boolean existsView = urlRecordRepository.existsByUrlAndPc(url, ipAddress);
        if (!existsView) {
            UrlRecord urlRecord = urlRecordFactory.parseUrlRecord(urlRecordPutViewCountRequestDto, ipAddress);
            urlRecordRepository.save(urlRecord);
            jobJpaRepository.findByUrl(url).ifPresentOrElse(
            v -> {
                v.stream().forEach(job -> job.updateViewCount(job.getViewCount()));
            },
            () -> {
                YoutubeAndNews youtubeAndNews = youtubeAndNewsJpaRepository.findByUrl(url).orElseThrow(()-> new NoCrawlingDataException());
                youtubeAndNews.updateViewCount(youtubeAndNews.getViewCount());
            });
        }
    }
}
