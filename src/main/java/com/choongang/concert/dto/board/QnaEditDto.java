package com.choongang.concert.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QnaEditDto {

    private Long id;
    private Long loginId;
    private String title;
    private String writer;
    private String content;
    private String category;


//    public QnaEditDto() {
//
//        this.writer = "관리자";
//    }
}
