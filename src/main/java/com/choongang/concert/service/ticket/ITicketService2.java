package com.choongang.concert.service.ticket;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
import com.choongang.concert.dto.ticket.ConcertInfoDto;
import com.choongang.concert.dto.ticket.RemainNumDto;
import com.choongang.concert.dto.ticket.SeatListDto;

@Service
public interface ITicketService2 {

	//	달력 선택한 날짜에 대한 좌석 잔여수 찾아오기
	List<ChoiceDateDto> selectedDate (int concertId);
	
	/**
     * 날짜별 좌석 뷰 업데이트
     */
    List<SeatListDto> getSeatRemain(String concertDate);
    /**
     * 날짜 기반 콘서트 이름, 장소, 시간 따오기
     */
    ConcertInfoDto findConcertInfo(String concertDate);
    /**
     * 날짜 기반 좌석 등급별 가격및 잔여좌석수
     * js 구현으로 기능 보류 (정보의 정확성 및 일관성 관련 db 로 구현할지 고민)
     */
    List<RemainNumDto> remainNum(String concertDate);
    /**
     * html 좌석구조 및 실제 좌석번호 매핑용 데이터
     */
    String originSeatMapping(int seatIndex);
}
