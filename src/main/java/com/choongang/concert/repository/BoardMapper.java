package com.choongang.concert.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.entity.Notice;
import com.choongang.concert.entity.QnaList;

@Mapper
public interface BoardMapper {
	
	// notice_board 
	List<Notice> noticeBoardFindAll();
	
	Notice notcieBoardFindByNum(Long noticeNum);
	
	int noticeFindAllCnt(Notice notice);
	
	
	// Qna_board
	List<QnaList> qnaBoardFindAll();
	
	QnaList qnaBoardFindByNum(Long qnaListNum);
	
	int qnaListFindAllCnt(QnaList qnaList);
}
