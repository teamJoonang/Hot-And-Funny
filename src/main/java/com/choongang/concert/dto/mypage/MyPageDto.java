package com.choongang.concert.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@RequiredArgsConstructor
@ToString
//@NoArgsConstructor
public class MyPageDto {


    private Long id;
    private String category;
    private String title;
    private Date createdAt;
    private Boolean responseYn;

}
