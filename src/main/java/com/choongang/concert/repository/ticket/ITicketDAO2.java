package com.choongang.concert.repository.ticket;

import com.choongang.concert.dto.ticket.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITicketDAO2 {

	//	날짜 선택시 좌석 잔여석 갖고 오기 calendar 화면
	List <ChoiceDateDto> remainingSeat(@Param("concertId") int concertId);
	
	//	티켓 정보 불러와서 ticket_check view 보여주기
	//	TicketShowDto ticketView(@Param("concertDate") String concertDate, @Param("seatNum")String SeatNum);
	List<TicketShowDto> ticketView(@Param ("concertDate") String concertDate, @Param ("userId") String userId);
	
	
	//	티켓 수 갖고 오기
	List<TicketCountDto> ticketCountMapper(@Param("userId") String userId);
	
	//	콘서트 정보
	List<ConcertDto> concertInfo (@Param("concertId") int concertId);
	
	//	javaScript에 콘서트 정보 보낼 DAO
	List<ConcertScriptDto> javascriptConcertInfo();
}
