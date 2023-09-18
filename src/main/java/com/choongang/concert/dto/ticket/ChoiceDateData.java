package com.choongang.concert.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDateData {

	private String remainingSeat; 		// 남은 좌석 종류
	private int remainingSeatCount;  // 좌석 남은 수 
	
}
