package com.choongang.concert.dto.mypage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class MyTicketDto {

    private String uuid;
    private String concertName;
    private String seatNumber;
    private String concertDate;
    private String concertTime;
    private String concertPlace;
    private String createdAt;
    private Status status;
}
