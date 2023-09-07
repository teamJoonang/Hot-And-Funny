package com.choongang.concert.controller.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.concert.service.ticket.TicketService;

@RestController
public class TicketApiController2 {
	
	@Autowired
	private TicketService ticketService;
	
}
