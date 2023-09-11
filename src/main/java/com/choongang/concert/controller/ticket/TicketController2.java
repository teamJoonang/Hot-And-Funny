package com.choongang.concert.controller.ticket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.choongang.concert.service.ticket.TicketService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ticket")

public class TicketController2 {
	
	private final TicketService ticketService;

	@GetMapping ("/home/calendar")
	public String homeCalendar () {
		return "ticket/home_calendar";
	}
	
	
	
	@GetMapping ("/approval")
	public String approval () {
		return "ticket/approval";
	}
	
	@GetMapping ("/ticket/check")
	public String ticketCheck() {
		return "ticket/ticket_check";
	}
	
//	@GetMapping("/home/calendar")
//	public String homeCalendar(@RequestParam("concertId") String concertId, Model model){
//		String cdd = concertId + 12345566;
//
//		model.addAttribute("cdd", cdd);
//		
//		return "ticket/home_calendar";		
//	}
}

