package com.choongang.concert.dto.mypage;

import lombok.Getter;

@Getter
public class MyTicketDto {

    private String uuid;
    private String ticketName;
    private String createdAt;
    private Enum<Status> status;
}
