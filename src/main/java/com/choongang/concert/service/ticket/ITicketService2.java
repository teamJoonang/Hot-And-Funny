package com.choongang.concert.service.ticket;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
import com.choongang.concert.dto.ticket.DateSeatDto;
import com.choongang.concert.dto.ticket.TicketShowDto;

@Service
public interface ITicketService2 {

	//	달력 선택한 날짜에 대한 좌석 잔여수 찾아오기
	List<ChoiceDateDto> selectedDate (int concertId);
	
	//	티켓 정보 가져오기
	List<TicketShowDto> getTicketInfo (DateSeatDto dst);
	
    
}
