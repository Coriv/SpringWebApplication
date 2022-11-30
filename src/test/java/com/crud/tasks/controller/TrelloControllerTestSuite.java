package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade facade;

    @Test
    void shouldFetchEmptyTrelloBoard() throws Exception {

        when(facade.fetchTrelloBoards()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("123", "Test list", false));
        List<TrelloBoardDto> trelloBoardsDto = List.of(new TrelloBoardDto("listId", "description", trelloLists));

        when(facade.fetchTrelloBoards()).thenReturn(trelloBoardsDto);
        //When&Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("listId")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", Matchers.is("123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", Matchers.is("Test list")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", Matchers.is(false)));

    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        //Given
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("id", "Test name", "https://test.com");
        TrelloCardDto trelloCardDto = new TrelloCardDto("Id1", "Card", "Test card", "top");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        when(facade.createCart(any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
        //When&Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("id")))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.name"), Matchers.is("Test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", Matchers.is("https://test.com")));
    }
}