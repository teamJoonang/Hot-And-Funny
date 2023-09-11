package com.choongang.concert.repository.ticket;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.choongang.concert.dto.ticket.ChoiceDateData;
import com.choongang.concert.dto.ticket.ConcertInfoDto;
import com.choongang.concert.dto.ticket.RemainNumDto;
import com.choongang.concert.dto.ticket.SeatListDto;

@Mapper
public interface ITicketDAO {

	// 날짜 선택시 좌석 잔여석 갖고 오기 calendar 화면
	List <ChoiceDateData> remainingSeat(@Param("concertId") int concertId);
	
	// 좌석선택 페이지 랜더링 시 기 예매좌석 비활성화
    List<SeatListDto> seatRemainView(@Param("concertDate") String concertDate);

    // 날짜 기반 콘서트 정보 따오기
    ConcertInfoDto findConcertInfo (@Param("concertDate") String concertDate);

    // 날짜 기반 잔여 좌석 수 (자바스크립트로 구현 했었는데 괜히만듬 시간낭비)
    List<RemainNumDto> findRemainNum(@Param("concertDate") String concertDate);

    // html 좌석구조 및 실제 좌석번호 매핑용 데이터
    String originSeatMapping(int seatIndex);
}
