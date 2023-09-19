package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class MakeTicketDto {
    private String seatNumber;
    private long concertId;
    private long userId;
    private boolean discountYn;
    private String seatGrade;
    private long seatPrice;
}
