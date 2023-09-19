package com.choongang.concert.controller.ticket;

import com.choongang.concert.dto.ticket.TitleShowDto;
import com.choongang.concert.service.ticket.TicketService2;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/ticket")
public class TicketRestController2 {

	private final TicketService2 ticketService;


	@GetMapping("/home/calendar/{concertId}")
	public ResponseEntity<List<TitleShowDto>> seat(@PathVariable String concertId) {
		//List<ChoiceDateDto> cdd = ticketService.selectedDate(Integer.parseInt(concertId));
		
		List<TitleShowDto> tsd = ticketService.titleShow(Integer.parseInt(concertId));

		return ResponseEntity.ok(tsd);
	}
}
