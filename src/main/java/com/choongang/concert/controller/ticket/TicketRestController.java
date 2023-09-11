package com.choongang.concert.controller.ticket;

import com.choongang.concert.dto.ticket.AccumulatePriceDto;
import com.choongang.concert.dto.ticket.OriginSeatNumberDto;
import com.choongang.concert.dto.ticket.SeatListDto;
import com.choongang.concert.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@Slf4j
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

//    // 기 예매 좌석 체크 및 js 사용 api컨트롤러  -- 비동기방식이 아니여서 일반컨트롤러 쓰자 동일 url 충돌 발생함
//    @GetMapping("/seat/choice/{concertDate}")
//    public ResponseEntity<List<SeatListDto>> seatChoiceAPI(@PathVariable String concertDate) {
//        List<SeatListDto> seatList = ticketService.getSeatRemain(concertDate);
//        return ResponseEntity.ok(seatList);
//    }
    // 실제 좌석번호 매핑용 json 데이터 js 사용 api 컨트롤러
    @GetMapping("/seat/choice/{concertDate}/{seatIndex}")
    public String OriginSeatAPI(@PathVariable String concertDate, @PathVariable int seatIndex) {
        String originSeatNumber = ticketService.originSeatMapping(seatIndex);
        return originSeatNumber;
    }

    @PostMapping("/payment/check")
    public int accumulatePriceAPI(@RequestBody AccumulatePriceDto accumulatePriceDto) {
        String grade = accumulatePriceDto.getGrade();
        int diff = accumulatePriceDto.getDiff();
        boolean disCountYN = accumulatePriceDto.isDisCountYN();
        System.out.println("CONTROLLER : "+ disCountYN);
        int accumulateResult = ticketService.accumulatePrice(grade, diff, disCountYN);
        return accumulateResult;
    }


    //재훈이 방법
//
//    private final TicketService ticketService;
//
//
//
////    public ResponseEntity<String> seatChoice(@RequestParam("concertDate") String concertDate) {
//
//    @GetMapping("/ticket/seat/choices/{concertDate}")
//    @ResponseBody
//    public List<Seat> seatChoice(@PathVariable String concertDate) {
//
//        Seat seat1 = new Seat(1L, "vip", "2023-09-09");
//        Seat seat2 = new Seat(2L, "vip", "2023-09-09");
//        Seat seat3 = new Seat(3L, "r", "2023-09-09");
//        Seat seat4 = new Seat(4L, "s", "2023-09-09");
//        Seat seat5 = new Seat(5L, "s", "2023-09-10");
//        Seat seat6 = new Seat(6L, "s", "2023-09-10");
//        Seat seat7 = new Seat(7L, "s", "2023-09-10");
//        Seat seat8 = new Seat(8L, "s", "2023-09-11");
//
//        List<Seat> seats = new ArrayList<>();
//        seats.add(seat1);
//        seats.add(seat2);
//        seats.add(seat3);
//        seats.add(seat4);
//        seats.add(seat5);
//        seats.add(seat6);
//        seats.add(seat7);
//        seats.add(seat8);
//
//        List<Seat> remainSeat = new ArrayList<>();
//
//        seats.stream().filter(seat -> seat.getDate().equals(concertDate)).forEach(seat -> remainSeat.add(seat));
//
//        return remainSeat;
//    }
//}
//
//@Getter
//class Seat {
//
//    Long id;
//    String type;
//    String date;
//
//    public Seat(Long id, String type, String date) {
//        this.id = id;
//        this.type = type;
//        this.date = date;
//    }
//}

//    기존 API 트라이
//    @Autowired
//    private TicketService ticketService;
//
//    @GetMapping("/ticket/seat/choices")
//    public ResponseEntity<String> seatChoice(@RequestParam("concertDate") String concertDate) {
//        // concertDate를 Date 형식으로 변환
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parsedDate;
//        try {
//            parsedDate = dateFormat.parse(concertDate);
//        } catch (ParseException e) {
//            // Date 형식으로 변환할 수 없는 경우 처리
//            // 예외 처리 로직 추가
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        log.info("parse ={}",parsedDate);
//        List<SeatListDto> remainSeat = ticketService.getSeatRemain(parsedDate);
//        System.out.println(remainSeat);
//        // JSON 직렬화를 위한 ObjectMapper 생성
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            // remainSeat 객체를 JSON 문자열로 변환
//            String jsonSeatList = objectMapper.writeValueAsString(remainSeat);
//            return ResponseEntity.ok(jsonSeatList);
//        } catch (JsonProcessingException e) {
//            // JSON 직렬화에 실패한 경우 처리
//            // 예외 처리 로직 추가
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
