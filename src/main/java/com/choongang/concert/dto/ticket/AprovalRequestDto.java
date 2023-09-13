package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class AprovalRequestDto {
    private String seatNumber;
    private int concertId;
    private int userId;
    private boolean discountYn;
}
