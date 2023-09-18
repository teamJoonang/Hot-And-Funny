package com.choongang.concert.service.board;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.EventDto;
import com.choongang.concert.dto.board.EventEditDto;
import com.choongang.concert.repository.board.EventMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
	
	private final EventMapper eventMapper;


    public List<EventDto> getEventList(CreatePageDto createPageDto){

    	System.out.println("서비스 시작");
    	List<EventDto> abcd = eventMapper.eventFindAll(createPageDto);
    	System.out.println("서비스 리턴" + abcd);
        return eventMapper.eventFindAll(createPageDto);
    }

    public int getEventAllCount(CreatePageDto createPageDto){

        return eventMapper.eventAllCount(createPageDto);
    }

    public EventDto findEventDetail(Long id) {

        return eventMapper.eventFindById(id);
    }

    public int eventCreatePost(EventEditDto eventEditDto) {

        return eventMapper.eventCreatePost(eventEditDto);
    }

    public int eventEditPost(EventEditDto eventEditDto) {
        return eventMapper.eventUpdatePost(eventEditDto);
    }

    public int eventDeletePost(Long id) {
        return eventMapper.eventDeletePost(id);
    }
    
	// 조회수 업그레이드 
	public EventDto eventFindViewPostById(Long id) {
		// 게시물 조회 
		EventDto et = eventMapper.eventFindById(id); 
		// 조회수 증가 
		eventMapper.eventUpdateViewCnt(id);
		return et;	
	}
}
