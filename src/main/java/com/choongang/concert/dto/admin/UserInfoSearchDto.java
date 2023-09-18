package com.choongang.concert.dto.admin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoSearchDto {

    private Long id;
    private String category;
    private String title;
    private String content;
    private String createdAt;
    private String writer;
    private Integer hit;
}
