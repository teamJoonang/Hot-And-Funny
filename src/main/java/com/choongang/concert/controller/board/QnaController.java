package com.choongang.concert.controller.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.PageDto;
import com.choongang.concert.dto.board.QnaDto;
import com.choongang.concert.dto.board.QnaEditDto;
import com.choongang.concert.service.board.QnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {
	
	private final QnaService qnaService;


	@GetMapping("/qna")
	public String getQnaView(@ModelAttribute CreatePageDto createPageDto, Model model){


		List<QnaDto> qnaList = qnaService.getQnaList(createPageDto);
		int qnaTotalCount = qnaService.getQnaAllCount(createPageDto);

		log.info("qnaList.size() = {}" ,qnaList.size());

		PageDto pageDto = new PageDto(createPageDto, qnaTotalCount);
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("pageDto", pageDto);

		return "/board/qna";
	}

	@GetMapping("/qna/detail/{id}")
	public String getQnaDetailView(@PathVariable Long id, Model model) {
		QnaDto qnaDetail = qnaService.findQnaDetail(id);
		QnaDto qnaView = qnaService.qnaFindViewPostById(id);
		model.addAttribute("qna", qnaDetail);
		model.addAttribute("qnaView", qnaView);
		return "board/qna_detail";
	}

	@GetMapping("/qna/create")
	public String getQnaCreateView() {
		return "board/qna_write";
	}

	@PostMapping("/qna/create")
	public String qnaCreatePost(@ModelAttribute QnaEditDto qnaEditDto){

		log.info("QnaEditDto = {}", qnaEditDto);
		// title입력 안할 경우 빈문자열 삽입됨, 클릭불가 현상 (임시조치)
		if (qnaEditDto.getTitle() == null || qnaEditDto.getTitle().isEmpty()){
			qnaEditDto.setTitle("제목없음");
		}
		int qnaRow = qnaService.qnaCreatePost(qnaEditDto);


		log.info("INSERT ROW = {}", qnaRow);
		return "redirect:/qna";
	}

	@GetMapping("/qna/edit/{id}")
	public String getQnaEditView(@PathVariable Long id, Model model){

		log.info("GET ID = {}", id);
		QnaDto qnaDetail = qnaService.findQnaDetail(id);
		model.addAttribute("qnaEdit", qnaDetail);

		return "/board/qna_edit";
	}


	@PostMapping("/qna/edit/{id}")
	public String updateQnaEdit(@PathVariable Long id, @ModelAttribute QnaEditDto qnaEditDto, Model model){

		int qnaRow = qnaService.qnaEditPost(qnaEditDto);
		log.info("UPDATE ROW = {}", qnaRow);

		return "redirect:/qna/detail/{id}";
	}

	@PostMapping("/qna/delete/{id}")
	public String deleteQna(@PathVariable Long id) {

		int qnaRow = qnaService.qnaDeletePost(id);
		log.info("DELETE ROW = {} ID = {}", qnaRow, id);

		return "redirect:/qna";
	}
}
