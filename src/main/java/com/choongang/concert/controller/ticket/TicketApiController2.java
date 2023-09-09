package com.choongang.concert.controller.ticket;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.concert.dto.ticket.ChoiceDateData;
import com.choongang.concert.service.ticket.TicketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/ticket/home/calendar")
public class TicketApiController2 {
	 
	private final TicketService ticketService;
	
//	@GetMapping("/ticket/home/calendar")
//	public List<ChoiceDateData> se(@RequestParam String concertId){
//		log.info("---------------1-----------"  + concertId);
//		List <ChoiceDateData> cdd = ticketService.selectedDate(Integer.parseInt(concertId));
//		log.info("---------------4-----------"  + concertId);
//		return cdd;
//	}
	
	@GetMapping("/{concertId}")
	public ResponseEntity<List<ChoiceDateData>> seat(@PathVariable String concertId){
		log.info("---------------1-----------"  + concertId);
		List <ChoiceDateData> cdd = ticketService.selectedDate(Integer.parseInt(concertId));
		log.info("---------------4-----------"  + concertId);
		return ResponseEntity.ok(cdd);
	}


}
