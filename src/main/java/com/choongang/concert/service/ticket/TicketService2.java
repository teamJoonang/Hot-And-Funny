package com.choongang.concert.service.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
import com.choongang.concert.dto.ticket.ConcertDto;
import com.choongang.concert.dto.ticket.ConcertScriptDto;
import com.choongang.concert.dto.ticket.TicketCountDto;
import com.choongang.concert.dto.ticket.TicketLimitDto;
import com.choongang.concert.dto.ticket.TicketShowDto;
import com.choongang.concert.dto.ticket.TitleShowDto;
import com.choongang.concert.repository.ticket.ITicketDAO2;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TicketService2 implements ITicketService2 {

	private final ITicketDAO2 ticketDao;

	// calendar 좌석 잔여석 갖고오기
	public List<ChoiceDateDto> selectedDate(int concertId) {
		List<ChoiceDateDto> cdd = ticketDao.remainingSeat(concertId);		
		return cdd;
	}

	
	//	콘서트 calendar 홈화면 타이틀 데이터 갖고오기
	public List<TitleShowDto> titleShow (int concertId) {

		List<ChoiceDateDto> cdd = ticketDao.remainingSeat(concertId);

		List<ConcertDto> cd = ticketDao.concertInfo(concertId);
		
		List<TitleShowDto> tsdList = new ArrayList<>();
		
		for (ChoiceDateDto choiceDateDto : cdd) {
			for (ConcertDto concertDto : cd) {
				TitleShowDto tsd = new TitleShowDto();
				
				tsd.setRemainingSeat(choiceDateDto.getRemainingSeat());
				tsd.setRemainingSeatCount(choiceDateDto.getRemainingSeatCount());
				
				tsd.setConcertDate(concertDto.getConcertDate());
				tsd.setConcertPlace(concertDto.getConcertPlace());
				tsd.setConcertRestrictedAge(concertDto.getConcertRestrictedAge());
				tsd.setConcertRuntime(concertDto.getConcertRuntime());
				tsdList.add(tsd);
			}
		}
		
		return tsdList;
	}
	
	
	// JavaScript에 concert 정보 저장하기
	public List<ConcertScriptDto> javascriptConcertInfo(){
		List<ConcertScriptDto> csdList = ticketDao.javascriptConcertInfo();
		
		log.info("-------------------1-----------"+csdList);
		return csdList;
	}
	
	// 티켓 생성 하기
	public List<TicketShowDto> getTicketInfo(String concertDate, String userId) {

		List<TicketShowDto> tsd = ticketDao.ticketView(concertDate, userId);
		return tsd;
	}

	
	
	// 하나의 아이디로 동일한 날짜 4개 초과 구매 불가능하게
	public List<TicketLimitDto> ticketLimit(String userId) {
		// Database에서 데이터 갖고옴
		List<TicketCountDto> tcdList = ticketDao.ticketCountMapper(userId);
		// 갖고온 데이터 ConcertId 별로 담음 (숫자를 새기위함)
		List<Integer> concertIds = new ArrayList<>();
	
		for (TicketCountDto tcd : tcdList) {
			String checkUserId = tcd.getCheckUserId();
			int concertId = tcd.getConcertId();
			// String concertDate = tcd.getConcertDate();
			if (userId.equals(checkUserId)) {
				concertIds.add(concertId);
			}
		}

		// 요소별 카운트를 저장할 맵 생성
		Map<Integer, Integer> countMap = new HashMap<>();

		// 리스트 순회하면서 요소별 카운트 계산
		for (int concertId : concertIds) {
		    if (countMap.containsKey(concertId)) {
		        // 이미 맵에 해당 요소가 존재하는 경우 카운트를 증가시킴
		        int count = countMap.get(concertId);
		        countMap.put(concertId, count + 1);
		    } else {
		        // 맵에 해당 요소가 없는 경우 초기값 1로 설정
		        countMap.put(concertId, 1);
		    }
		}
		
		List<TicketLimitDto> tldList = new ArrayList<>();
		// 결과 출력 TicketLimitDto List에 담기 
		for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
			int concertId = entry.getKey();
		    int count = entry.getValue();
		    
		    TicketLimitDto tld = new TicketLimitDto();
		    tld.setConcertId(concertId);
		    tld.setTicketCount(count);
		    tldList.add(tld);
		    log.info(tld);
		}
	
		
		return tldList;
	}
}
