package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.domain.exception.NotExistsRequestParamException;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.presentation.dto.contents.CategoryCountDto;
import com.hermes.ithermes.presentation.dto.contents.ContentsDtoInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ResponseEntity<List<ContentsDtoInterface>> getMainContents(@RequestParam(value = "type") CategoryType type){
        if(!CategoryType.isContainCategoryType(type.getTitle())){
            throw new NotExistsRequestParamException();
        }
        return ResponseEntity.ok(contentsService.getMainContents(type));
    }


    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ResponseEntity<List<ContentsDtoInterface>> getCategoryContents(@RequestParam(value = "type") CategoryType type, @RequestParam(value = "page")int page,
                                                                          @RequestParam(value = "order",required = false)OrderType order){
        if(!CategoryType.isContainCategoryType(type.getTitle())||!OrderType.isContainOrderType(order.getName())){
            throw new NotExistsRequestParamException();
        }
        return ResponseEntity.ok(contentsService.getCategoryContents(type, page,order));
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public ResponseEntity<CategoryCountDto> getCategoryCount(){
        return ResponseEntity.ok(contentsService.getCategoryCount());
    }


}
