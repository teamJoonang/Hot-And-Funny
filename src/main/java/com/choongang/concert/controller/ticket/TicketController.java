package com.choongang.concert.controller.ticket;

import com.choongang.concert.dto.ticket.ConcertInfoDto;
import com.choongang.concert.dto.ticket.SeatListDto;
import com.choongang.concert.service.ticket.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ticket")
@Slf4j
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/seat/choice/{concertDate}")
    public String seatChoice(@PathVariable String concertDate, Model model) {
        // js 에서 데이터를 사용하기 위해서는 반드시 서버에서 json 으로 파싱해서 보내야함
        List<SeatListDto> remainSeat = ticketService.getSeatRemain(concertDate);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // remainSeat 객체를 JSON 문자열로 변환
            String jsonSeatList = objectMapper.writeValueAsString(remainSeat);
            
            model.addAttribute("remainSeat", jsonSeatList);

        } catch (JsonProcessingException e) {
            return "좌석 파싱 에러";
        }
        /* // concertDate를 Date 형식으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(concertDate);
        } catch (ParseException e) {
            // Date 형식으로 변환할 수 없는 경우 처리
            // 예외 처리 로직 추가
            return "error"; // 예외 처리에 따른 적절한 응답을 반환하거나 처리합니다.
        }
        log.info("parse ={}",parsedDate);
        List<SeatListDto> remainSeat = ticketService.getSeatRemain(parsedDate);
        model.addAttribute(("remainNum"), remainNum);
*/
        ConcertInfoDto concertInfo = ticketService.findConcertInfo(concertDate);
        model.addAttribute("concertInfo", concertInfo);
        model.addAttribute("concertDate", concertDate);
        return "ticket/seat_choice";

    }

    @GetMapping("/payment/check")
    public String paymentCheck(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        long userId = (long)session.getAttribute("id");
        System.out.println("sessionId : " + userId);
        model.addAttribute("userId", userId);
        return "ticket/payment_check";
    }

//    @GetMapping("/payment/check")
//    public String paymentCheck() {
//        return "ticket/payment_check";
//    }


}


