package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper mapper;

    @Test
    void mapToBoardsTest() {
        //Given
        List<TrelloBoardDto> boardsDto = new ArrayList<>();
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "TestList", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("1", "TestList2", false);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "Test", Arrays.asList(trelloListDto1, trelloListDto2));
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "Test2", Arrays.asList(trelloListDto1, trelloListDto2));
        boardsDto.addAll(Arrays.asList(trelloBoardDto1, trelloBoardDto2));

        //When
        List<TrelloBoard> boards = mapper.mapToBoards(boardsDto);

        //Then
        assertEquals(2, boards.size());
        assertEquals(2, boards.get(0).getLists().size());
    }

    @Test
    void mapToBoardsDtoTest() {
        //Given
        List<TrelloBoard> boards = new ArrayList<>();
        TrelloList trelloList1 = new TrelloList("1", "TestList", false);
        TrelloList trelloList2 = new TrelloList("1", "TestList2", false);
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "Test", Arrays.asList(trelloList1, trelloList2));
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "Test2", Arrays.asList(trelloList1, trelloList2));
        boards.addAll(Arrays.asList(trelloBoard1, trelloBoard2));

        //When
        List<TrelloBoardDto> boardsDto = mapper.mapToBoardsDto(boards);

        //Then
        assertEquals(2, boardsDto.size());
        assertEquals(2, boardsDto.get(0).getLists().size());
    }

    @Test
    void mapToListTest() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "TestList", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("1", "TestList2", false);

        List<TrelloListDto> listsDto = Arrays.asList(trelloListDto1, trelloListDto2);

        //When
        List<TrelloList> lists = mapper.mapToList(listsDto);

        //Then
        assertEquals(2, lists.size());
        assertEquals("TestList", lists.get(0).getName());
    }

    @Test
    void mapToListDtoTest() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "TestList", false);
        TrelloList trelloList2 = new TrelloList("1", "TestList2", false);

        List<TrelloList> lists = Arrays.asList(trelloList1, trelloList2);

        //When
        List<TrelloListDto> listsDto = mapper.mapToListDto(lists);

        //Then
        assertEquals(2, lists.size());
        assertEquals("TestList", lists.get(0).getName());
    }

    @Test
    void mapToCardTest() {
        //Given
        TrelloCardDto cartDto = new TrelloCardDto("212", "Something", "Else", "Here");

        //When
        TrelloCard card = mapper.mapToCard(cartDto);

        //Then
        assertEquals("212", card.getListId());
        assertEquals("Something", card.getName());
        assertEquals("Else", card.getDescription());
        assertEquals("Here", card.getPos());
    }

    @Test
    void mapToCardDtoTest() {
        //Given
        TrelloCard cart = new TrelloCard("212", "Something", "Else", "Here");

        //When
        TrelloCardDto cardDto = mapper.mapToCardDto(cart);

        //Then
        assertEquals("212", cardDto.getIdList());
        assertEquals("Something", cardDto.getName());
        assertEquals("Else", cardDto.getDesc());
        assertEquals("Here", cardDto.getPos());
    }
}