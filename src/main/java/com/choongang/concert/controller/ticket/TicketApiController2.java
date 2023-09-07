package com.choongang.concert.controller.ticket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.concert.domain.ticket.RequestData;
import com.choongang.concert.service.ticket.TicketService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/ticket/home/calendar")
@Log4j2
public class TicketApiController2 {
	
	@Autowired
	private TicketService ticketService;
	
	
	@PostMapping
	public ResponseEntity<?> handleRequest(Model model, @RequestBody RequestData requestData) {
        // requestData.year, requestData.month 등에 액세스할 수 있습니다.
        // Java에서 요청 데이터를 처리하는 로직을 작성하세요.
        // ...
        // 필요한 경우 응답 반환
        // 예를 들어 JSON 응답을 반환할 수 있습니다.
        Map<String, Object> response = new HashMap<>();
        response.put("message", "요청이 성공적으로 처리되었습니다." + requestData);
        return ResponseEntity.ok(response);
		
    }


}
