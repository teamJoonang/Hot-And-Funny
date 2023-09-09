package com.choongang.concert.service.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.concert.dto.ticket.ChoiceDateData;
import com.choongang.concert.repository.ticket.TicketDAO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TicketService {

	@Autowired
	private TicketDAO ticketDao;
	
	public List<ChoiceDateData> selectedDate (int concertId){
		log.info("---------------2-----------"  + concertId);
		List<ChoiceDateData> cdd = ticketDao.remainingSeat(concertId);
		log.info("---------------3-----------"  + concertId);	
		return cdd;
	}
}
