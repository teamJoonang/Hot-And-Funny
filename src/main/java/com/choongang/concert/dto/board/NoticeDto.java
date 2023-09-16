package com.choongang.concert.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeDto {

    private String title;
    private String writer;
    private String content;
    private Integer hit;
    private String createdAt;
    private String category;
}
