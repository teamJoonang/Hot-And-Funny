package com.choongang.concert.controller.board;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.NoticeDto;
import com.choongang.concert.dto.board.NoticeEditDto;
import com.choongang.concert.dto.board.PageDto;
import com.choongang.concert.service.board.BoardService;
import jakarta.servlet.http.HttpSession;
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
		model.addAttribute("notice", noticeDetail);

		return "board/basic_detail";
	}

	@GetMapping("/notice/create")
	public String getNoticeCreateView() {

		return "board/admin_write";
	}

	@PostMapping("/notice/create")
	public String createPost(@ModelAttribute NoticeEditDto noticeEditDto){

		log.info("NoticeEditDto = {}", noticeEditDto);
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



}
