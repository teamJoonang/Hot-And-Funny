package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class RemainNumDto {
    private String seatType;
    private int seatPrice;
    private int remainingSeats;
}