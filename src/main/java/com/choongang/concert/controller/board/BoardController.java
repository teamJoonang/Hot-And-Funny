package com.choongang.concert.controller.board;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.NoticeDto;
import com.choongang.concert.dto.board.PageDto;
import com.choongang.concert.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;


	@GetMapping("/notice")
	public String getNoticeView(CreatePageDto createPageDto, Model model){


		List<NoticeDto> noticeList = boardService.getNoticeList(createPageDto);
		int totalCount = boardService.getNoticeAllCount(createPageDto);

		log.info("noticeList.size() = {}" ,noticeList.size());

		PageDto pageDto = new PageDto(createPageDto, totalCount);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pageDto", pageDto);

		return "/board/notice";
	}

}
