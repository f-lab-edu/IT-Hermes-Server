package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.JobService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.job.JobInsertRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job")
public class JobController {
    private final JobService jobService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> insertJob(@RequestBody JobInsertRequestDto jobInsertRequestDto){
        CommonResponseDto commonResponseDto = jobService.insertJob(jobInsertRequestDto);
        return ResponseEntity.created(URI.create("/api/job")).body(commonResponseDto);
    }

}
