package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TrelloClientTest {
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Test
    void shouldFetchTrelloBoards() throws URISyntaxException {
        trelloClient = new TrelloClient(restTemplate, trelloConfig);
        //Given
        when(trelloConfig.getTrelloApiClient()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("testKey");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        when(trelloConfig.getUsername()).thenReturn("testUser");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "Kodilla", new ArrayList<>());

        URI uri = new URI("http://test.com/members/testUser/boards?key=testKey&token=test&fields=name,%20id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("Kodilla", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        trelloClient = new TrelloClient(restTemplate, trelloConfig);
        // Given
        when(trelloConfig.getTrelloApiClient()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");

        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20Description&desc=top&pos=test_id&idList=Test%20task");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "test task",
                "http://test.com"
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);
        // When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        // Then
        assertEquals("1", newCard.getId());
        assertEquals("test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    void shouldReturnEmptyList() throws URISyntaxException {
        //Given
        trelloClient = new TrelloClient(restTemplate, trelloConfig);

        when(trelloConfig.getTrelloApiClient()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("testKey");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        when(trelloConfig.getUsername()).thenReturn("testUser");

        URI uri = new URI("http://test.com/members/testUser/boards?key=testKey&token=test&fields=name,%20id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> list = trelloClient.getTrelloBoards();

        //Then
        assertEquals(0, list.size());
    }
}
