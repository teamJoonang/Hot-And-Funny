package com.choongang.concert.controller.ticket;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
import com.choongang.concert.dto.ticket.DateSeatDto;
import com.choongang.concert.dto.ticket.TicketShowDto;
import com.choongang.concert.service.ticket.TicketService2;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/ticket")
public class TicketRestController2 {

	private final TicketService2 ticketService;


	@GetMapping("/home/calendar/{concertId}")
	public ResponseEntity<List<ChoiceDateDto>> seat(@PathVariable String concertId) {
		List<ChoiceDateDto> cdd = ticketService.selectedDate(Integer.parseInt(concertId));
		return ResponseEntity.ok(cdd);
	}

//	@GetMapping("/ticket/check/{concertDate}/{userId}")
//	public ResponseEntity<List<TicketShowDto>> ticketCheck(@PathVariable String concertDate, @PathVariable String userId) {
//		//consumes = MediaType.APPLICATION_JSON_VALUE
//
//		List <TicketShowDto> tsd = ticketService.getTicketInfo(concertDate, userId);
//		log.info(tsd);
////		for (TicketShowDto tsd : tsdList) {
////			String uuid = tsd.getUuid();
////			String concertDate = tsd.getConcertDate();
////			String seatNum = tsd.getSeatNum();
////			model.addAttribute("uuid", uuid);
////			model.addAttribute("concertDate", concertDate);
////			model.addAttribute("seatNum", seatNum);
////			log.info(uuid);
////			log.info(concertDate);
////			log.info(seatNum);
////		}		
//		return ResponseEntity.ok(tsd);
//	}
}
