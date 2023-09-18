package com.choongang.concert.service.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.NoticeDto;
import com.choongang.concert.dto.board.NoticeEditDto;
import com.choongang.concert.repository.board.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;


    public List<NoticeDto> getNoticeList(CreatePageDto createPageDto){

        return boardMapper.findAll(createPageDto);
    }

    public int getNoticeAllCount(CreatePageDto createPageDto){

        return boardMapper.allCount(createPageDto);
    }

    public NoticeDto findNoticeDetail(Long id) {

        return boardMapper.findById(id);
    }

    public int createPost(NoticeEditDto noticeEditDto) {

        return boardMapper.createPost(noticeEditDto);
    }

    public int editPost(NoticeEditDto noticeEditDto) {
        return boardMapper.updatePost(noticeEditDto);
    }

    public int deletePost(Long id) {
        return boardMapper.deletePost(id);
    }
    
	// 조회수 업그레이드 
	public NoticeDto findViewPostById(Long id) {
		// 게시물 조회 
		NoticeDto nt = boardMapper.findById(id); 
		// 조회수 증가 
		boardMapper.updateViewCnt(id);
		return nt;	
	}
	
	/*
	 * // 카테고리 종목별로 모으기 public List<NoticeDto> findAll() { return
	 * boardMapper.findCategoryAll(); }
	 * 
	 * public List<NoticeDto> findNotices() { return boardMapper.findNotices(); }
	 * 
	 * public List<NoticeDto> findEvents() { return boardMapper.findEvents(); }
	 * 
	 * 
	 * public List<NoticeDto> findQnAs() { return boardMapper.findQnAs(); }
	 */
	

	}

	

	

	

