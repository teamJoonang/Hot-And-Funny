package com.choongang.concert.controller.board;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.NoticeDto;
import com.choongang.concert.dto.board.NoticeEditDto;
import com.choongang.concert.dto.board.PageDto;
import com.choongang.concert.service.board.NoticeService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;


	@GetMapping("/notice")
	public String getNoticeView(@ModelAttribute CreatePageDto createPageDto, Model model){


		List<NoticeDto> noticeList = noticeService.getNoticeList(createPageDto);
		int totalCount = noticeService.getNoticeAllCount(createPageDto);

		log.info("noticeList.size() = {}" ,noticeList.size());

		PageDto pageDto = new PageDto(createPageDto, totalCount);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pageDto", pageDto);

		return "/board/notice";
	}

	@GetMapping("/notice/detail/{id}")
	public String getNoticeDetailView(@PathVariable Long id, Model model) {
		NoticeDto noticeDetail = noticeService.findNoticeDetail(id);
		NoticeDto noticeView = noticeService.findViewPostById(id);
		model.addAttribute("notice", noticeDetail);
		model.addAttribute("noticeView", noticeView);
		return "board/notice_detail";
	}

	@GetMapping("/notice/create")
	public String getNoticeCreateView() {
		return "board/notice_write";
	}

	@PostMapping("/notice/create")
	public String createPost(@ModelAttribute NoticeEditDto noticeEditDto){

		log.info("NoticeEditDto = {}", noticeEditDto);
		// title입력 안할 경우 빈문자열 삽입됨, 클릭불가 현상 (임시조치)
		if (noticeEditDto.getTitle() == null || noticeEditDto.getTitle().isEmpty()){
			noticeEditDto.setTitle("제목없음");
		}
		int row = noticeService.createPost(noticeEditDto);


		log.info("INSERT ROW = {}", row);
		return "redirect:/notice";
	}

	@GetMapping("/notice/edit/{id}")
	public String getNoticeEditView(@PathVariable Long id, Model model){

		log.info("GET ID = {}", id);
		NoticeDto noticeDetail = noticeService.findNoticeDetail(id);
		model.addAttribute("edit", noticeDetail);

		return "/board/edit";
	}


	@PostMapping("/notice/edit/{id}")
	public String updateNoticeEdit(@PathVariable Long id, @ModelAttribute NoticeEditDto noticeEditDto, Model model){

		int row = noticeService.editPost(noticeEditDto);
		log.info("UPDATE ROW = {}", row);

		return "redirect:/notice/detail/{id}";
	}

	@PostMapping("/notice/delete/{id}")
	public String deleteNotice(@PathVariable Long id) {

		int row = noticeService.deletePost(id);
		log.info("DELETE ROW = {} ID = {}", row, id);

		return "redirect:/notice";
	}
	
	
	

}
