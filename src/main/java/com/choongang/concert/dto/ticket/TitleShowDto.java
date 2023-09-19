package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class TitleShowDto {
	
	//	concertDto + ChoiceDateDto (일봉)
	
	private String remainingSeat; 			// 남은 좌석 종류
	private int remainingSeatCount;  		// 좌석 남은 수 
	
	private String concertPlace; 			// 콘서트 장소
	private String concertDate;				//	관람 시간
	private String concertRuntime;			//	콘서트 날짜
	private String concertRestrictedAge;	//	관람 등급
	
	
}
