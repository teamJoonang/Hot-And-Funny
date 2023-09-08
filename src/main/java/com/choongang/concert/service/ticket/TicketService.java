package com.choongang.concert.service.ticket;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.choongang.concert.domain.ticket.ChoiceDateData;
import com.choongang.concert.domain.ticket.TicketDao;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TicketService {


	private TicketDao ticketDao;
	
	public List<ChoiceDateData> selectedDate (RequestBody requestBody){
		
		return ticketDao.requestData(requestBody);
	}
}
