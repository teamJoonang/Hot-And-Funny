package com.choongang.concert.service.ticket;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
import com.choongang.concert.dto.ticket.TicketCountDto;
import com.choongang.concert.dto.ticket.TicketShowDto;
import com.choongang.concert.repository.ticket.ITicketDAO2;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TicketService2 implements ITicketService2{

	private final ITicketDAO2 ticketDao;
	
	//	calendar 좌석 잔여석 갖고오기
	public List<ChoiceDateDto> selectedDate (int concertId){
		List<ChoiceDateDto> cdd = ticketDao.remainingSeat(concertId);
		String abc = cdd.get(0).getRemainingSeat();
		int cd = cdd.get(1).getRemainingSeatCount();
		return cdd;
	}
		
	public List<TicketShowDto> getTicketInfo(String concertDate, String userId){
		
		List<TicketShowDto> tsd = ticketDao.ticketView(concertDate, userId);
		return tsd;
	}
	
	public List<TicketCountDto> ticketCountInfo (String userId) {

		List<TicketCountDto> tcm = ticketDao.ticketCountMapper(userId);
		
		return tcm;
	};

}
