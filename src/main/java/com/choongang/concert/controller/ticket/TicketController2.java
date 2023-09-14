package com.choongang.concert.controller.ticket;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.choongang.concert.dto.ticket.TicketShowDto;
import com.choongang.concert.service.ticket.TicketService2;

import jakarta.servlet.http.HttpSession;
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

	@GetMapping("/ticket/check")
	public String ticketCheck(@RequestParam String concertDate, Model model, HttpSession session) {
		
//		sesssion {id : 2}
		Long id = (Long)session.getAttribute("Id");
		String userId = String.valueOf(id);
//		concertDto user = new ConcertDto();
//		user.setId(id);
		log.info("-----------------1-------------------" + id);
		log.info("-----------------2-------------------" + userId);
		List<TicketShowDto> tsdList = ticketService.getTicketInfo(concertDate, userId);
	
//		for (TicketShowDto tsd : tsdList) {
//			String uuid = tsd.getUuid();
//			String choiceDate = tsd.getConcertDate();
//			String seatNum = tsd.getSeatNum();
//			model.addAttribute("uuid", uuid);
//			model.addAttribute("concertDate", choiceDate);
//			model.addAttribute("seatNum", seatNum);
//			log.info(uuid);
//			log.info(choiceDate);
//			log.info(seatNum);
//		}
		model.addAttribute("tickets", tsdList);
		return "ticket/ticket_check";
	}
}