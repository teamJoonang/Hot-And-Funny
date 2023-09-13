package com.choongang.concert.controller.ticket;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.choongang.concert.dto.ticket.DateSeatDto;
import com.choongang.concert.dto.ticket.TicketShowDto;
import com.choongang.concert.service.ticket.TicketService2;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2

@RequestMapping("/ticket")
public class TicketController2 {

	
	private final TicketService2 ticketService;

	@GetMapping("/home/calendar")
	public String homeCalendar() {
		return "ticket/home_calendar";
	}

	@GetMapping("/approval")
	public String approval() {
		return "ticket/approval";
	}

	@PostMapping("/ticket/check")
	public String ticketCheck2() {
		
		return "ticket/ticket_check";
	}
	
//	@PostMapping(value = "/ticket/check", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public String ticketCheck(@RequestBody DateSeatDto dst, Model model) {
//
//		List <TicketShowDto> tsdList = ticketService.getTicketInfo(dst);
//		
//		for (TicketShowDto tsd : tsdList) {
//			String uuid = tsd.getUuid();
//			String concertDate = tsd.getConcertDate();
//			String seatNum = tsd.getSeatNum();
//			model.addAttribute("uuid", uuid);
//			model.addAttribute("concertDate", concertDate);
//			model.addAttribute("seatNum", seatNum);
//			log.info(uuid);
//			log.info(concertDate);
//			log.info(seatNum);
//		}
//		
//		return "ticket/ticket_check";
//	}
}
