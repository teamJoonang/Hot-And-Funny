package com.choongang.concert.service.ticket;

import com.choongang.concert.dto.ticket.*;

import java.util.Date;
import java.util.List;

public interface ITicketService {

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
    /**
     * 금액 누적 ajax 용 서비스
     */
    int accumulatePrice(String grade, int diff, boolean discountYN);
    /**
     * 결재완료 트랜젝션 처리
     */
//    List<T> paymentAproval(AprovalRequestDto aprovalRequestDto);
}
