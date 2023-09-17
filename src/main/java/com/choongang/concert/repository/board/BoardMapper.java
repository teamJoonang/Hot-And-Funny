package com.choongang.concert.repository.board;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.NoticeDto;
import com.choongang.concert.dto.board.PageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<NoticeDto> findAll(CreatePageDto createPageDto);

    NoticeDto findById(Long id);

    int allCount(CreatePageDto createPageDto);


}
