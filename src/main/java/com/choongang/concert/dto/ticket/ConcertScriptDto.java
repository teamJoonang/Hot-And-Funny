package com.choongang.concert.dto.ticket;

import lombok.Data;

@Data
public class ConcertScriptDto {

	//	Script에 넣을 데이터 일봉
	private int concertId;
	private String concertName;
	private String concertPlace;
	private String concertYear;
	private String concertMonth;
	private String concertDay;
	private String concertTime;
	private String concertRuntime;
	private String concertRestrictedAge;
}
