package com.choongang.concert.controller.ticket;

import java.util.List;

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
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j

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
	public String ticketCheck(@RequestBody DateSeatDto dst, Model model) {
//		String concertDate = dst.getConcertDate();
//		String seatNum = dst.getSeatNum();
		TicketShowDto tsd = ticketService.getTicketInfo(dst);
		model.addAttribute("uuid", tsd.getUuid());
		model.addAttribute("concertDate", tsd.getConcertDate());
		model.addAttribute("seatNum", tsd.getSeatNum());
		return "ticket/ticket_check";
	}

}
