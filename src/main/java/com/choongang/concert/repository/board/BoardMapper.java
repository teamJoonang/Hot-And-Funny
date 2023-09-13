package com.choongang.concert.repository.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.board.PageNoticeDto;
import com.choongang.concert.entity.board.EventList;
import com.choongang.concert.entity.board.Notice;
import com.choongang.concert.entity.board.QnaList;

@Mapper
public interface BoardMapper {
	
//	// notice_board 
//	List<Notice> noticeBoardFindAll();
	
	// 게시글 리스트 조회 @return 게시글 리스트 
	List<Notice> noticeFindAll();
	
	// 게시글 수 카운팅 return 게시글 수 
	int noticeFindByNumCnt();
	
	

//--------------------------------------------------------------------------------------------
	
	

	// Qna_board
//	List<QnaList> qnaBoardFindAll();
	
//	List<QnaList> qnaBoardFindAll(Long qnaListNum);
//	
//	int qnaListFindAllCnt(QnaList qnaList);
	
	
	// event_list 
//	List<EventList> eventBoardFindAll();
	
//	EventList eventBoardFindAll(Long eventListNum);
//	
//	int eventFindAllCnt(EventList eventList);
}
