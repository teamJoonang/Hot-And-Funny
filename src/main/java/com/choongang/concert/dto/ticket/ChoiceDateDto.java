package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data

public class ChoiceDateDto {

	private String remainingSeat; 		// 남은 좌석 종류
	private int remainingSeatCount;  // 좌석 남은 수 
	
}
