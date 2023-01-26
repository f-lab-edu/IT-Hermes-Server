package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.JobService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.job.JobInsertRequestDto;
import com.hermes.ithermes.presentation.dto.job.JobLastUrlRequestDto;
import com.hermes.ithermes.presentation.dto.job.JobLastUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> parseJob(@RequestBody JobInsertRequestDto jobInsertRequestDto){
        CommonResponseDto commonResponseDto = jobService.parseJob(jobInsertRequestDto);
        return ResponseEntity.created(URI.create("/job")).body(commonResponseDto);
    }

    @RequestMapping(value = "/last-url",method = RequestMethod.POST)
    public ResponseEntity<JobLastUrlResponseDto> findYoutubeAndNewsLastUrl(@RequestBody JobLastUrlRequestDto jobLastUrlRequestDto){
        JobLastUrlResponseDto jobLastUrlResponseDto = jobService.findJobLastUrl(jobLastUrlRequestDto);
        return ResponseEntity.ok(jobLastUrlResponseDto);
    }
}
