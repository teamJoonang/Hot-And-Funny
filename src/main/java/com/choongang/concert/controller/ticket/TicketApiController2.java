package com.choongang.concert.controller.ticket;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.concert.dto.ticket.ChoiceDateData;
import com.choongang.concert.service.ticket.TicketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class TicketApiController2 {
	 
	private final TicketService ticketService;
	
	@GetMapping("/ticket/home/calendar")
	public List<ChoiceDateData> seat(@RequestParam int concertId){
		List <ChoiceDateData> cdd = ticketService.selectedDate(concertId);
		return cdd;
	}


}
