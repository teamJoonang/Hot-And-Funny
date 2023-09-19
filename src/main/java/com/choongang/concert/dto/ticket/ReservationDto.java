package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
// 일봉
public class ReservationDto {
	private String remainingSeat; 		// 남은 좌석 종류
	private int remainingSeatCount;  // 좌석 남은 수 
	private String checkUserId;	 // session값과 체크할 유저아이디
	private String ticketCount; // 티켓 구매 수
}
