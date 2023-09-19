package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class ConcertDto {
	
	// 고일봉 사용함
	// concert 데이터
	
	private int concertId;
	private String concertName;
	private String concertPlace;
	private String concertDate;
	private String concertRuntime;
	private String concertRestrictedAge;
}
