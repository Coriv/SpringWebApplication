package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleMailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void fetchTrelloBoardsTest() {
        //Given
        List<TrelloBoardDto> boards = new ArrayList<>();
        TrelloListDto trelloList1 = new TrelloListDto("1", "TestList", false);
        TrelloListDto trelloList2 = new TrelloListDto("1", "TestList2", false);
        TrelloBoardDto trelloBoard1 = new TrelloBoardDto("1", "Test", Arrays.asList(trelloList1, trelloList2));
        TrelloBoardDto trelloBoard2 = new TrelloBoardDto("2", "Test2", Arrays.asList(trelloList1, trelloList2));
        boards.addAll(Arrays.asList(trelloBoard1, trelloBoard2));

        when(trelloClient.getTrelloBoards()).thenReturn(boards);
        //When
        List<TrelloBoardDto> resultBoards = trelloService.fetchTrelloBoards();
        String listName = boards.get(1).getLists().get(0).getName();
        String resultListName = resultBoards.get(1).getLists().get(0).getName();

        //Then
        assertEquals(resultBoards.size(), 2);
        assertEquals(listName, resultListName);
    }

    @Test
    void fetchCreateTrelloCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("123", "Card 1", "Testing card", "Top");
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("1", "Card1", "https://test.com");
        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCardDto);
        //When
        CreatedTrelloCardDto resultCardDto = trelloService.createTrelloCard(cardDto);
        //
        assertEquals(resultCardDto.getId(), createdCardDto.getId());
        assertEquals(resultCardDto.getName(), createdCardDto.getName());
        assertEquals(resultCardDto.getShortUrl(), "https://test.com");
        verify(emailService, times(1)).send(any());
    }
}