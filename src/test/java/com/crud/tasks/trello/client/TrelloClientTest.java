package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
        System.out.println(trelloConfig.getTrelloApiClient());
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        /*
        TrelloBoardDto[] list = restTemplate.getForObject(uri, TrelloBoardDto[].class);

        System.out.println(trelloConfig.getTrelloApiClient());

        List<TrelloBoardDto> fetchedTrelloBoards = Optional.ofNullable(list)
                .map(Arrays::asList)
                .orElse(Collections.emptyList())
                .stream()
                .filter(n -> Objects.nonNull(n.getId()) && Objects.nonNull(n.getName()))
                .collect(Collectors.toList()); */

        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("Kodilla", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }
}