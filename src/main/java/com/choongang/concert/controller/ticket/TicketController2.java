package com.choongang.concert.controller.ticket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketController2 {

//	@GetMapping ("/ticket/home/calendar")
//	public String homeCalendar () {
//		return "ticket/home_calendar";
//	}
	
	@GetMapping ("/ticket/approval")
	public String approval () {
		return "ticket/approval";
	}
	
	@GetMapping ("/ticket/ticket/check")
	public String ticketCheck() {
		return "ticket/ticket_check";
	}
}
