package com.choongang.concert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.concert.entity.Notice;
import com.choongang.concert.entity.QnaList;
import com.choongang.concert.repository.BoardMapper;

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
	
	
	// Qna_Board 게시판 
	public List<QnaList> qnaListBoard(){
		return boardMapper.qnaBoardFindAll();
	}
}
