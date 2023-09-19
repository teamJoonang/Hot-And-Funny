package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data

public class ApprovalDto {

	private String userId;
	private String concertDate;
	private int price;
	private int resultTotalPrice;
	private String seatNumber;
	
}
