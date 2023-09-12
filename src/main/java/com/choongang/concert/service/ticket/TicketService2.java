package com.choongang.concert.service.ticket;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
import com.choongang.concert.dto.ticket.ConcertInfoDto;
import com.choongang.concert.dto.ticket.RemainNumDto;
import com.choongang.concert.dto.ticket.SeatListDto;
import com.choongang.concert.repository.ticket.ITicketDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TicketService implements ITicketService{

	private final ITicketDAO ticketDao;
	
	//	calendar 좌석 잔여석 갖고오기
	public List<ChoiceDateDto> selectedDate (int concertId){
		List<ChoiceDateDto> cdd = ticketDao.remainingSeat(concertId);
		log.info("---------------------------------------------------------cdd::{}" , cdd);
		String abc = cdd.get(0).getRemainingSeat();
		int cd = cdd.get(1).getRemainingSeatCount();
		log.info("-----------------------------------1----------------------cd::{}" , cd);
		return cdd;
	}
	
	 // 날짜별 좌석 뷰 업데이트
    public List<SeatListDto> getSeatRemain(String concertDate) {
        return ticketDao.seatRemainView(concertDate);
    }
    // 날짜 기반 콘서트 정보 따오기
    public ConcertInfoDto findConcertInfo(String concertDate) {
        return ticketDao.findConcertInfo(concertDate);
    }
    // 날짜 기반 잔여 좌석 수 업데이트 (자바스크립트로 구현 했었는데 괜히만듬 시간낭비)
    public List<RemainNumDto> remainNum(String concertDate) {
        return ticketDao.findRemainNum(concertDate);
    }
    // html 좌석구조 및 실제 좌석번호 매핑용 데이터
    public String originSeatMapping(int seatIndex) {
        return ticketDao.originSeatMapping(seatIndex);

    };

}
