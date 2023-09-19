package com.choongang.concert.controller.ticket;

import com.choongang.concert.dto.ticket.ConcertScriptDto;
import com.choongang.concert.dto.ticket.TicketLimitDto;
import com.choongang.concert.dto.ticket.TicketShowDto;
import com.choongang.concert.service.ticket.TicketService2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2

@RequestMapping("/ticket")
public class TicketController2 {

	
	private final TicketService2 ticketService;

	@GetMapping("/home/calendar")
	public String homeCalendar(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession(false);
		if(session != null && session.getAttribute("id") != null) {
			long id = (long) session.getAttribute("id");
			
			String userId = String.valueOf(id);
			List<TicketLimitDto> tldList = ticketService.ticketLimit(userId);
			
	
			model.addAttribute("tldList", tldList);
			model.addAttribute("userId", userId);
	
			
			
//			List<TicketCountDto> tcdList = ticketService.ticketCountInfo(userId);
//			List<String> checkUserIds = new ArrayList<>();
//			List<String> limitConcertDates = new ArrayList<>();
//			
//			for (TicketCountDto tcd : tcdList) {
//				String checkUserId = tcd.getCheckUserId();
//				String limitConcertDate = tcd.getConcertDate();
//				checkUserIds.add(checkUserId);
//				limitConcertDates.add(limitConcertDate);
//			}
//			model.addAttribute("checkUserId", checkUserIds);
//			model.addAttribute("limitConcertDate", limitConcertDates);
//			log.info("-------------1--------" + checkUserIds);
//			log.info("-------------2--------" + limitConcertDates);
			
			
//			return "redirect:/user/login";
//			return "redirect:/ticket/home_calendar";
		}
		List<ConcertScriptDto> csdList = ticketService.javascriptConcertInfo(); 
		log.info("-------------------1-----------"+csdList);
		model.addAttribute("cdList", csdList);
		
		return "ticket/home_calendar";
	}

	@GetMapping("/approval")
	public String approval() {
		return "ticket/approval";
	}

	@GetMapping("/ticket/check")
	public String ticketCheck(@RequestParam String concertDate, Model model, HttpSession session) {
		
		long id = (long)session.getAttribute("id");
		String userId = String.valueOf(id);

		List<TicketShowDto> tsdList = ticketService.getTicketInfo(concertDate, userId);

		model.addAttribute("tickets", tsdList);
		return "ticket/ticket_check";
	}
}