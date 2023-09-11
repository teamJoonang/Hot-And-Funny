package com.choongang.concert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.choongang.concert.entity.Notice;
import com.choongang.concert.entity.QnaList;
import com.choongang.concert.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// 요청을 board로 라우팅 
@RequestMapping("/board")
// 해당클래스의 필드에 대한 생성자가 자동으로 생성 중복코드를 작성을 피할 수 있다.
@RequiredArgsConstructor
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/admin/write")
	public String adminWrite() {
		return "board/admin_write";
	}

	@GetMapping("/basic/detail")
	public String basicDetail() {
		return "board/basic_detail";
	}

	@GetMapping("/event/card")
	public String eventCard() {
		return "board/event_card";
	}

	@GetMapping("/event/detail")
	public String eventDetail() {
		return "board/event_detail";
	}

	@GetMapping("/event/list")
	public String eventList() {
		return "board/event_list";
	}

//	@GetMapping("/notice/list")
//	// @ModelAttribute Notice notice: HTTP 요청 파라미터를 자동으로 Notice 클래스에 매핑합니다. 
//	public String displayNotice(@ModelAttribute Notice notice,
//			
//	// @RequestParam(value = "page", defaultValue = "1") 
//	// int page: page라는 이름의 요청 파라미터를 받습니다. 만약 요청 파라미터가 없으면 기본값으로 1을 사용합니다.
//			@RequestParam(value = "page", defaultValue = "1") 
//			int page, Model model) {
//		int count = boardService.findByNoticeCount(notice);
//		log.info("---------------------------count");
//		PageNationDto pageNationDto = new PageNationDto(count, page);
//		List<Notice> noticeList = boardService.findNoticeList(pageNationDto);
//		
//		log.info("-------------------------------Notice = {}", noticeList.get(0));
//		log.info("notice count = {}", count);
//		
//		model.addAttribute("noticeList", noticeList);
//		model.addAttribute("page", page);
//		model.addAttribute("pageDto", pageNationDto);
//		model.addAttribute("count", count);
//		
//		return "board/notice_list";
//		
//	}
	
	@GetMapping("/notice/list")
	public String noticeList(Model model) {
		List<Notice> noticeBoard = boardService.noticeBoard();
		model.addAttribute("noticeBoard", noticeBoard);
		log.info("noticeBoard : " + noticeBoard);
		return "board/notice_list";
	}

	
	
//	@GetMapping("/notice/{num}")
//	public String displayNoticeDetail(@PathVariable Long num, 
//												Model model) {
//		Notice notice = boardService.findByNum(num);
//		
//		log.info("notice detail = {}", notice.toString());
//		
//		model.addAttribute("notice", notice);
//		
//		return "board/basic_detail";
//	}
//	
	
	

	@GetMapping("/qna/list")
	public String qnaListBoard(Model model2) {
		List<QnaList> qnaListBoard = boardService.qnaListBoard();
		model2.addAttribute("qnaListBoard", qnaListBoard);
		return "board/qna_list";
	}
	
//	@GetMapping("/qna/list")
////	 @ModelAttribute QnaList qnaList :@ModelAttribute 어노테이션은 메서드의 파라미터로 사용되며,
////	 요청 파라미터를 QnaList 객체에 자동으로 바인딩합니다.
////	 즉, HTTP 요청의 파라미터를 QnaList 객체의 필드에 매핑합니다.
//	public String qnaList(@ModelAttribute QnaList qnaList, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
//		
//		int count = boardService.findByQnaListCount(qnaList);
//		PageQnaListDto pageQnaListDto = new PageQnaListDto(count, page);
//		List<QnaList> qnaListList = boardService.findQnaList(pageQnaListDto);
//		
//		log.info("QnaList = {}", qnaListList.get(0));
//		log.info("qnaList count = {}", count);
//		
//		model.addAttribute("qnaListList", qnaListList);
//		model.addAttribute("page",page);
//		model.addAttribute("pageDto", pageQnaListDto);
//		model.addAttribute("count",count);
//		
//		return "board/qna_list";
//	}



	@GetMapping("/user/write?{id}")
	public String userwrite() {
		return "/board/user_write";
	}
}
