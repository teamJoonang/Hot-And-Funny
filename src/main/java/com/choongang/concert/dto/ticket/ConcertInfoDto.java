package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class ConcertInfoDto {
    private String concertName;
    private String concertPlace;
    private String formattedTime;
}
