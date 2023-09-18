package com.choongang.concert.repository.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.EventDto;
import com.choongang.concert.dto.board.EventEditDto;

@Mapper
public interface EventMapper {
	List<EventDto> eventFindAll(CreatePageDto createPageDto);

    EventDto eventFindById(Long id);

    int eventAllCount(CreatePageDto createPageDto);

    int eventCreatePost(EventEditDto eventEditDto);

    int eventUpdatePost(EventEditDto eventEditDto);

    int eventDeletePost(Long id);

    void eventUpdateViewCnt(Long id);
}
