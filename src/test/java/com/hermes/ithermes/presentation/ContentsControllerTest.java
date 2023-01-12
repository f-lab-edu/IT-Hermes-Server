package com.hermes.ithermes.presentation;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.presentation.controller.ContentsController;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

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
    @DisplayName("ContentsType에 존재하지 않는 enum값으로 요청시에 400 상태코드가 반환되어야 한다.")
    void notExistsContentTypeTest() throws Exception {
        String badContentsType="ABCDE";

        List<MainPageContentsDto> mainPageContentsDtoList=new ArrayList<>();
        mainPageContentsDtoList.add(new MainPageContentsDto("안녕하세요.","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎ","ㅎㅎㅎㅎ"));

        when(contentsService.getMainContents(badContentsType)).thenReturn(mainPageContentsDtoList);

        mockMvc.perform(get("/contents/main")
                        .param("type",badContentsType))
                        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("ContentType에 존재하는 enum값으로 요청시에 200 성공 상태코드가 반환되어야 한다.")
    void existsContentTypeTest() throws Exception {
        String contentsType = "JOB";

        List<MainPageContentsDto> mainPageContentsDtoList = new ArrayList<>();
        mainPageContentsDtoList.add(new MainPageContentsDto("안녕하세요.", "ㅎㅎㅎㅎㅎㅎ", "ㅎㅎㅎㅎㅎ", "ㅎㅎㅎㅎㅎㅎ", "ㅎㅎㅎㅎ", "ㅎㅎㅎㅎ"));

        when(contentsService.getMainContents(contentsType)).thenReturn(mainPageContentsDtoList);

        mockMvc.perform(get("/contents/main")
                        .param("type",contentsType))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("존재하지 않는 enum값으로 contentType이나 orderType을 요청시 400 코드가 반환되어야 한다.")
    void notExistContentTypeOrOrderType()throws Exception{
        String badContentsType="FGH";
        String badOrderType="ABCDE";

        List<ContentsDto> contentsDtoList=new ArrayList<>();
        contentsDtoList.add(new ContentsDto("안녕하세요","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ"));

        when(contentsService.getCategoryContents(badContentsType,0,badOrderType)).thenReturn(contentsDtoList);

        mockMvc.perform(get("/contents/category")
                .param("type",badContentsType)
                        .param("page", String.valueOf(0))
                        .param("order",badOrderType))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하는 contents,order 타입값으로 요청시 200 성공 코드가 반환되어야 한다.")
    void existContentTypeOrOrderType()throws Exception{
        String contentsType="JOB";
        String orderType="RECENT";

        List<ContentsDto> contentsDtoList=new ArrayList<>();
        contentsDtoList.add(new ContentsDto("안녕하세요","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ","ㅎㅎㅎㅎㅎ"));

        when(contentsService.getCategoryContents(contentsType,0,orderType)).thenReturn(contentsDtoList);

        mockMvc.perform(get("/contents/category")
                .param("type",contentsType)
                .param("page",String.valueOf(0))
                .param("order",orderType))
                .andExpect(status().isOk());
    }


}