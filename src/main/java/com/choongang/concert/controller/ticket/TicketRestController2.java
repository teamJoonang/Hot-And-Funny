package com.choongang.concert.controller.ticket;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.concert.dto.ticket.ChoiceDateDto;
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

}
