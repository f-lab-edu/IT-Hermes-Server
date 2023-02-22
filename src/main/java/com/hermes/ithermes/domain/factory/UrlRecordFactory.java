package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.UrlRecord;
import com.hermes.ithermes.infrastructure.UrlRecordRepository;
import com.hermes.ithermes.presentation.dto.urlrecord.UrlRecordPutViewCountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlRecordFactory {
    private final UrlRecordRepository urlRecordRepository;

    public UrlRecord parseUrlRecord(UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto, String ipAddress) {
        UrlRecord urlRecord = UrlRecord
                .builder()
                .url(urlRecordPutViewCountRequestDto.getUrl())
                .clientIpAddress(ipAddress)
                .build();

        return urlRecord;
    }
}
