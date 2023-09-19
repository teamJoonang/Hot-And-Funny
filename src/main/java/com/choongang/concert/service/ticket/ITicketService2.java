package com.choongang.concert.service.ticket;

import com.choongang.concert.dto.ticket.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITicketService2 {

	//	달력 선택한 날짜에 대한 좌석 잔여수 찾아오기
	List<ChoiceDateDto> selectedDate (int concertId);
	
	//	콘서트 calendar 홈화면 타이틀 데이터 갖고오기
	List<TitleShowDto> titleShow (int concertId);
	
	
	//	티켓 정보 가져오기
	List<TicketShowDto> getTicketInfo (String concertDate, String userId);
	
	//	티켓 수 정보 가져오기
	List<TicketLimitDto> ticketLimit (String userId);

	//	자바스크립트에 콘서트 정보 넣기
	List<ConcertScriptDto> javascriptConcertInfo();
	
}
