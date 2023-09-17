package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class TicketLimitDto {

	private int concertId;
	private int ticketCount;
}
