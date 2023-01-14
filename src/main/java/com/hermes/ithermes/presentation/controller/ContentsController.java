package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.domain.exception.NotExistsRequestException;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.DtoInterface;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ResponseEntity<List<DtoInterface>> getMainContents(@RequestParam(value = "type")CategoryType type){
        if(!CategoryType.categoryTypeContains(type.getName())){
            throw new NotExistsRequestException();
        }
        return ResponseEntity.ok(contentsService.getMainContents(type));
    }


    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ResponseEntity<List<DtoInterface>> getCategoryContents(@RequestParam(value = "type") CategoryType type, @RequestParam(value = "page")int page,
                                                                  @RequestParam(value = "order",required = false)OrderType order){
        if(!CategoryType.categoryTypeContains(type.getName())||!OrderType.orderTypeContains(order.getName())){
            throw new NotExistsRequestException();
        }
        return ResponseEntity.ok(contentsService.getCategoryContents(type, page,order));
    }


}
