package com.choongang.concert.repository.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.NoticeDto;
import com.choongang.concert.dto.board.NoticeEditDto;

@Mapper
public interface BoardMapper {

    List<NoticeDto> findAll(CreatePageDto createPageDto);

    NoticeDto findById(Long id);

    int allCount(CreatePageDto createPageDto);

    int createPost(NoticeEditDto noticeEditDto);

    int updatePost(NoticeEditDto noticeEditDto);

    int deletePost(Long id);

    void updateViewCnt(Long id);
    
    //카테고리별 합치는 상단 메뉴바
    List<NoticeDto> findCategoryAll();  // 전체 종목 리스트
    List<NoticeDto> findNotices();		// 이벤트 리스트
    List<NoticeDto> findEvents();		// 공지사항 리스트
    List<NoticeDto> findQnAs();			// Q&A 리스트 
}
