package com.choongang.concert.service.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.concert.dto.board.PageNoticeDto;
import com.choongang.concert.entity.board.EventList;
import com.choongang.concert.entity.board.Notice;
import com.choongang.concert.entity.board.QnaList;
import com.choongang.concert.repository.board.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
// 클래스 필드 기반 자동 생성 
@RequiredArgsConstructor
public class BoardService {


	@Autowired
	private BoardMapper boardMapper;
	
	
	// notice_Board 게시판
	public List<Notice> noticeBoard() {
		return boardMapper.noticeBoardFindAll();
	}
	
	// notice 페이징 기능 
	public List<Notice> noticeFindByNumPost(final PageNoticeDto params) {
		 return boardMapper.noticeFindByNum(params);
	}


	
//--------------------------------------------------------------------------------------------------------------------
	
	
	
	// Qna_Board 게시판 
	public List<QnaList> qnaListBoard(){
		return boardMapper.qnaBoardFindAll();
	}


//--------------------------------------------------------------------------------------------------------------------

	
	
	// Event_Board 게시판 
	public List<EventList> eventBoard() {
		return boardMapper.eventBoardFindAll();
	}
}
