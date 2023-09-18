package com.choongang.concert.controller.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.NoticeDto;
import com.choongang.concert.dto.board.NoticeEditDto;
import com.choongang.concert.dto.board.PageDto;
import com.choongang.concert.service.board.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;


	@GetMapping("/notice")
	public String getNoticeView(@ModelAttribute CreatePageDto createPageDto, Model model){


		List<NoticeDto> noticeList = boardService.getNoticeList(createPageDto);
		int totalCount = boardService.getNoticeAllCount(createPageDto);

		log.info("noticeList.size() = {}" ,noticeList.size());

		PageDto pageDto = new PageDto(createPageDto, totalCount);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pageDto", pageDto);

		return "/board/notice";
	}

	@GetMapping("/notice/detail/{id}")
	public String getNoticeDetailView(@PathVariable Long id, Model model) {
		NoticeDto noticeDetail = boardService.findNoticeDetail(id);
		NoticeDto noticeView = boardService.findViewPostById(id);
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
		int row = boardService.createPost(noticeEditDto);


		log.info("INSERT ROW = {}", row);
		return "redirect:/notice";
	}

	@GetMapping("/notice/edit/{id}")
	public String getNoticeEditView(@PathVariable Long id, Model model){

		log.info("GET ID = {}", id);
		NoticeDto noticeDetail = boardService.findNoticeDetail(id);
		model.addAttribute("edit", noticeDetail);

		return "/board/edit";
	}


	@PostMapping("/notice/edit/{id}")
	public String updateNoticeEdit(@PathVariable Long id, @ModelAttribute NoticeEditDto noticeEditDto, Model model){

		int row = boardService.editPost(noticeEditDto);
		log.info("UPDATE ROW = {}", row);

		return "redirect:/notice/detail/{id}";
	}

	@PostMapping("/notice/delete/{id}")
	public String deleteNotice(@PathVariable Long id) {

		int row = boardService.deletePost(id);
		log.info("DELETE ROW = {} ID = {}", row, id);

		return "redirect:/notice";
	}
	
	// 카테고리별 종목 모으기 

	
	/*
	 * // 카테고리별 종목 모으기
	 * 
	 * @GetMapping("/notices") public String getNotices(Model model) {
	 * List<NoticeDto> notices = boardService.findNotices();
	 * model.addAttribute("notices", notices); return "notices"; // notices.html
	 * 템플릿을 참조하는 뷰 이름 }
	 * 
	 * @GetMapping("/events") public String getEvents(Model model) { List<NoticeDto>
	 * events = boardService.findEvents(); model.addAttribute("notices", events);
	 * return "notices"; // notices.html 템플릿을 참조하는 뷰 이름 }
	 * 
	 * 
	 * @GetMapping("/qnas") public String getQnAs(Model model) { List<NoticeDto>
	 * qnas = boardService.findQnAs(); model.addAttribute("notices", qnas); return
	 * "notices"; // notices.html 템플릿을 참조하는 뷰 이름 }
	 */
	

}
