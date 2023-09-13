package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class TicketShowDto {
	private String uuid;
	private String concertDate;
	private String seatNum;
}
