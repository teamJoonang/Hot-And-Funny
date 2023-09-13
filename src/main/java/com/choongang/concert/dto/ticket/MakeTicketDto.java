package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class MakeTicketDto {
    private String seatNumber;
    private int concertId;
    private int userId;
    private boolean discountYn;
    private String seatGrade;
    private int seatPrice;
}
