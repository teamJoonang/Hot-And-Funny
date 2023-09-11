package com.choongang.concert.service.ticket;

import com.choongang.concert.dto.ticket.ConcertInfoDto;
import com.choongang.concert.dto.ticket.OriginSeatNumberDto;
import com.choongang.concert.dto.ticket.RemainNumDto;
import com.choongang.concert.dto.ticket.SeatListDto;
import com.choongang.concert.repository.ticket.ITicketDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TicketService implements ITicketService {

    @Autowired
    private ITicketDao ticketDao;

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
    // 금액 누적 ajax 용 서비스
    public int accumulatePrice(String grade, int diff, boolean discountYN) {
        int basicPrice = ticketDao.gradePrice(grade);
        int diffPrice;
        System.out.println(discountYN);
        if(discountYN == false) {
            diffPrice = basicPrice * diff;
        } else {
            // 100 이하단위 버림 처리
            int datasort = (int) (basicPrice * 0.7) * diff;
            diffPrice = (datasort / 100) * 100;
        }
        return diffPrice;
    }

}
