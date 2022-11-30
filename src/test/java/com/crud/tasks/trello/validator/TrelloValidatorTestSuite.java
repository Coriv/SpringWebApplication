package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class TrelloValidatorTestSuite {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void shouldLogInfoAboutTest() {
        TrelloCard trelloCard = new TrelloCard("12", "test", "testDesc", "top");
        trelloValidator.validateCard(trelloCard);
    }

    @Test
    void shouldLogInfoAboutTask() {
        TrelloCard trelloCard = new TrelloCard("12", "name", "testDesc", "top");
        trelloValidator.validateCard(trelloCard);
    }

    @Test
    void shouldFilterTestTrelloBoards() {
        //Given
        List<TrelloList> trelloList = List.of(new TrelloList("ListId", "Test", false));
        TrelloBoard trelloTestBoard = new TrelloBoard("123", "test", trelloList);
        TrelloBoard trelloBoard = new TrelloBoard("324", "No test", trelloList);

        List<TrelloBoard> boardsList = Arrays.asList(trelloBoard, trelloTestBoard);
        //When
        List<TrelloBoard> resultList = trelloValidator.validateTrelloBoards(boardsList);
        //Then
        Assertions.assertEquals(resultList.size(), 1);
    }
}