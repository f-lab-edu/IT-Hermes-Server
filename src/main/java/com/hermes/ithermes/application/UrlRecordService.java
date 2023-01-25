package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.UrlRecordRepository;
import com.hermes.ithermes.presentation.dto.urlrecord.UrlRecordPutViewCountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlRecordService {
    @Autowired
    private final UrlRecordRepository urlRecordRepository;

    @Transactional
    public String putViewCount(UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto) {
        return null;
    }
}
