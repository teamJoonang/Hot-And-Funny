package com.choongang.concert.repository.ticket;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.choongang.concert.dto.ticket.ConcertInfoDto;
import com.choongang.concert.dto.ticket.MakeTicketDto;
import com.choongang.concert.dto.ticket.RemainNumDto;
import com.choongang.concert.dto.ticket.SeatListDto;

@Mapper
public interface ITicketDao {
    // 좌석선택 페이지 랜더링 시 기 예매좌석 비활성화
    List<SeatListDto> seatRemainView(@Param("concertDate") String concertDate);

    // 날짜 기반 콘서트 정보 따오기
    ConcertInfoDto findConcertInfo (@Param("concertDate") String concertDate);

    // 날짜 기반 잔여 좌석 수 (자바스크립트로 구현 했었는데 괜히만듬 시간낭비)
    List<RemainNumDto> findRemainNum(@Param("concertDate") String concertDate);

    // html 좌석구조 및 실제 좌석번호 매핑용 데이터
    String originSeatMapping(int seatIndex);

    // 금액 누적 ajax 용 서비스
    int gradePrice(@Param("grade") String grade);

    // 티켓 생성(insert into)
    void insertTickets(List<MakeTicketDto> makeTicketDtos);
    // 고객 잔액 확인 메소드
    int findUserCash(long userId);
    // 잔액 업데이트
    void updateCash(@Param("userId") long userId, @Param("remainCash") long remainCash);
}
