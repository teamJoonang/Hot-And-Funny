package com.choongang.concert.repository.ticket;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
import com.choongang.concert.dto.ticket.TicketCountDto;
import com.choongang.concert.dto.ticket.TicketShowDto;

@Mapper
public interface ITicketDAO2 {

	//	날짜 선택시 좌석 잔여석 갖고 오기 calendar 화면
	List <ChoiceDateDto> remainingSeat(@Param("concertId") int concertId);
	
	//	티켓 정보 불러와서 ticket_check view 보여주기
	//	TicketShowDto ticketView(@Param("concertDate") String concertDate, @Param("seatNum")String SeatNum);
	List<TicketShowDto> ticketView(@Param ("concertDate") String concertDate, @Param ("userId") String userId);
	
	//	티켓 수 갖고 오기
	List<TicketCountDto> ticketCountMapper(@Param("userId") String userId);
}
