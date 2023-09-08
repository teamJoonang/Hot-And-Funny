package com.choongang.concert.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDateData {

	private String concertId;
	private int remainingSeatVip; 	// VIP 좌석 남은수
	private int remainingSeatR;		// R 좌석 남은수
	private int remainingSeatS;		// S 좌석 남은수

}
