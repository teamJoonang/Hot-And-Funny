package com.choongang.concert.dto.board;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Notice {

    private Long id;
    private Long adminId;
    private String title;
    private String writer;
    private String content;
    private Integer hit;
    private String createdAt;
    private String category;

}
