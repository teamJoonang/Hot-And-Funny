package com.choongang.concert.controller.board;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.EventDto;
import com.choongang.concert.dto.board.EventEditDto;
import com.choongang.concert.dto.board.PageDto;
import com.choongang.concert.service.board.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Slf4j
@Controller
@Log4j2
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;


	@GetMapping("/event")
	public String getEventView(@ModelAttribute CreatePageDto createPageDto, Model model){

		System.out.println("컨트롤러 시작");
		List<EventDto> eventList = eventService.getEventList(createPageDto);
		System.out.println("컨트롤러 반환값 : " + eventList);
		int eventTotalCount = eventService.getEventAllCount(createPageDto);

		log.info("eventList.size() = {}" ,eventList.size());

		PageDto pageDto = new PageDto(createPageDto, eventTotalCount);
		model.addAttribute("eventList", eventList);
		model.addAttribute("pageDto", pageDto);

		return "/board/event";
	}

	@GetMapping("/event/detail/{id}")
	public String getEventDetailView(@PathVariable Long id, Model model) {
		EventDto eventDetail = eventService.findEventDetail(id);
		EventDto eventView = eventService.eventFindViewPostById(id);
		model.addAttribute("event", eventDetail);
		model.addAttribute("eventView", eventView);
		return "board/event_detail";
	}

	@GetMapping("/event/create")
	public String getEventCreateView() {
		return "board/event_write";
	}

	@PostMapping("/event/create")
	public String eventCreatePost(@ModelAttribute EventEditDto eventEditDto){

		log.info("EventEditDto = {}", eventEditDto);
		// title입력 안할 경우 빈문자열 삽입됨, 클릭불가 현상 (임시조치)
		if (eventEditDto.getTitle() == null || eventEditDto.getTitle().isEmpty()){
			eventEditDto.setTitle("제목없음");
		}
		int eventRow = eventService.eventCreatePost(eventEditDto);


		log.info("INSERT ROW = {}", eventRow);
		return "redirect:/event";
	}

	@GetMapping("/event/edit/{id}")
	public String getEventEditView(@PathVariable Long id, Model model){

		log.info("GET ID = {}", id);
		EventDto eventDetail = eventService.findEventDetail(id);
		model.addAttribute("eventEdit", eventDetail);

		return "/board/event_edit";
	}


	@PostMapping("/event/edit/{id}")
	public String updateEventEdit(@PathVariable Long id, @ModelAttribute EventEditDto eventEditDto, Model model){

		int eventRow = eventService.eventEditPost(eventEditDto);
		log.info("UPDATE ROW = {}", eventRow);

		return "redirect:/event/detail/{id}";
	}

	@PostMapping("/event/delete/{id}")
	public String deleteEvent(@PathVariable Long id) {

		int eventRow = eventService.eventDeletePost(id);
		log.info("DELETE ROW = {} ID = {}", eventRow, id);

		return "redirect:/event";
	}
}
