package com.choongang.concert.service.ticket;

import com.choongang.concert.dto.ticket.*;
import com.choongang.concert.exception.ticket.CashNotSufficientException;
import com.choongang.concert.repository.ticket.ITicketDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TicketService implements ITicketService {


    private final ITicketDao ticketDao;

    @Autowired
    public TicketService(ITicketDao ticketDao) {
        this.ticketDao =ticketDao;
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
    // 금액 누적 ajax 용 서비스
    public int accumulatePrice(String grade, int diff, boolean discountYN) {
        int basicPrice = ticketDao.gradePrice(grade);
        int diffPrice;
        if(!discountYN) {
            diffPrice = basicPrice * diff;
        } else {
            // 100 이하단위 버림 처리
            int datasort = (int) (basicPrice * 0.7) * diff;
            diffPrice = (datasort / 100) * 100;
        }
        return diffPrice;
    }
    @Transactional
    // 티켓 생성 서비스
    public void insertTickets(List<MakeTicketDto> makeTicketDtos) {
        int totalPrice = 0;
        long userId = 0;
        int charge = 2000;
        for (MakeTicketDto makeTicketDto : makeTicketDtos) {
            userId = makeTicketDto.getUserId();
            String seatGrade = makeTicketDto.getSeatGrade();
            long seatPrice = makeTicketDto.getSeatPrice();
            boolean discountYn = makeTicketDto.isDiscountYn();
            if (discountYn) {
                int typeChange = (int) (seatPrice * 0.7);
                int down = (typeChange / 100) * 100;
                totalPrice += down;
                totalPrice += charge;
            } else {
                totalPrice += seatPrice;
                totalPrice += charge;
            }
        }
        ticketDao.insertTickets(makeTicketDtos);
        int userCash = ticketDao.findUserCash(userId);
        int remainCash = userCash - totalPrice;
        if(remainCash < 0 ) {
            // 예외 처리: 사용자의 현금이 부족한 경우
            throw new CashNotSufficientException("사용자의 현금이 부족합니다.");
        }
        ticketDao.updateCash(userId, remainCash);


    }
}
