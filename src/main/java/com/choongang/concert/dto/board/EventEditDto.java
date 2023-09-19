package com.choongang.concert.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventEditDto {

    private Long id;
    private String title;
    private String writer;
    private String content;
    private String category;


    public EventEditDto() {

        this.writer = "관리자";
    }
}
