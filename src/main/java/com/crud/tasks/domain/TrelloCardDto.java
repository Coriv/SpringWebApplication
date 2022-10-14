package com.crud.tasks.domain;

import lombok.Data;

@Data
public class TrelloCardDto {

    private String idList;
    private String name;
    private String desc;
    private String pos;
}
