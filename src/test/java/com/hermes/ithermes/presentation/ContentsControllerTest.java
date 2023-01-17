package com.hermes.ithermes.presentation;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.OrderType;
import com.hermes.ithermes.presentation.controller.ContentsController;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.ContentsDtoInterface;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ContentsController.class)
class ContentsControllerTest {

    @MockBean
    private ContentsService contentsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("CategoryType에 존재하지 않는 enum값으로 요청 파라미터에 요청시에 400 상태코드가 반환되어야 한다.")
    void notExistsCategoryTypeTest() throws Exception {
        String wrongCategoryType= "ABCDE";

        List<ContentsDtoInterface> mainPageContentsDtoList=new ArrayList<>();
        mainPageContentsDtoList.add(new MainPageContentsDto("안녕하세요.","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎㅎ", ContentsProviderType.NAVER, LocalDateTime.now()));

        when(contentsService.getMainContents(any())).thenReturn(mainPageContentsDtoList);

        mockMvc.perform(get("/contents/main")
                        .param("type", wrongCategoryType))
                        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CategoryType에 존재하는 enum값으로 요청 파라미터에 요청시에 200 상태코드가 반환되어야 한다.")
    void existsCategoryTypeTest() throws Exception {
        CategoryType correctCategoryType = CategoryType.JOB;

        List<ContentsDtoInterface> mainPageContentsDtoList = new ArrayList<>();
        mainPageContentsDtoList.add(new MainPageContentsDto("안녕하세요.", "ㅎㅎㅎㅎㅎㅎ", "ㅎㅎㅎㅎㅎ", "ㅎㅎㅎㅎㅎㅎ", ContentsProviderType.NAVER, LocalDateTime.now()));

        when(contentsService.getMainContents(any())).thenReturn(mainPageContentsDtoList);

        mockMvc.perform(get("/contents/main")
                        .param("type", String.valueOf(correctCategoryType)))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("categoryType이나 orderType에 존재하지 않는 enum값으로 요청 파라미터에 요청시 400 코드가 반환되어야 한다.")
    void notExistCategoryTypeAndOrderTypeTest()throws Exception{
        String wrongContentsType="FGH";
        String wrongOrderType="ABCDE";

        List<ContentsDtoInterface> contentsDtoList=new ArrayList<>();
        contentsDtoList.add(new ContentsDto("안녕하세요","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ",ContentsProviderType.NAVER,LocalDateTime.now(),"ㅎㅎㅎㅎㅎ"));

        when(contentsService.getCategoryContents(any(),anyInt(),any())).thenReturn(contentsDtoList);

        mockMvc.perform(get("/contents/category")
                        .param("type",wrongContentsType)
                        .param("page", String.valueOf(0))
                        .param("order",wrongOrderType))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CategoryType나 orderType에 존재하는 enum값으로 요청 파라미터에 요청시에 200 상태코드가 반환되어야 한다.")
    void existCategoryTypeAndOrderTypeTest()throws Exception{
        CategoryType correctCategoryType = CategoryType.JOB;
        OrderType correctOrderType=OrderType.RECENT;

        List<ContentsDtoInterface> contentsDtoList=new ArrayList<>();
        contentsDtoList.add(new ContentsDto("안녕하세요","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ",ContentsProviderType.NAVER,LocalDateTime.now(),"ㅎㅎㅎㅎㅎ"));

        when(contentsService.getCategoryContents(any(),anyInt(),any())).thenReturn(contentsDtoList);

        mockMvc.perform(get("/contents/category")
                .param("type", String.valueOf(correctCategoryType))
                .param("page",String.valueOf(0))
                .param("order", String.valueOf(correctOrderType)))
                .andExpect(status().isOk());
    }


}

