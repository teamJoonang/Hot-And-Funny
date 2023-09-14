package com.choongang.concert.controller.ticket;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.choongang.concert.dto.ticket.TicketShowDto;
import com.choongang.concert.service.ticket.TicketService2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2

@RequestMapping("/ticket")
public class TicketController2 {

	
	private final TicketService2 ticketService;

//	@GetMapping("/home/calendar")
//	public String homeCalendar(HttpServletRequest req, Model model) {
//		HttpSession session = req.getSession();
//		Long userId = (Long) session.getAttribute("id");
//		model.addAttribute("userId", userId);
//		return "ticket/home_calendar";
//	}
	@GetMapping("/home/calendar")
	public String homeCalendar() {
	
		return "ticket/home_calendar";
	}

	@GetMapping("/approval")
	public String approval() {
		return "ticket/approval";
	}

	@GetMapping("/ticket/check")
	public String ticketCheck(@RequestParam String concertDate, Model model, HttpSession session) {
		
		Long id = (Long)session.getAttribute("id");
		String userId = String.valueOf(id);

		List<TicketShowDto> tsdList = ticketService.getTicketInfo(concertDate, userId);

		model.addAttribute("tickets", tsdList);
		return "ticket/ticket_check";
	}
}