package com.choongang.concert.repository.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.QnaDto;
import com.choongang.concert.dto.board.QnaEditDto;

@Mapper
public interface QnaMapper {
	
	List<QnaDto> qnaFindAll(CreatePageDto createPageDto);

    QnaDto qnaFindById(Long id);

    int qnaAllCount(CreatePageDto createPageDto);

    int qnaCreatePost(QnaEditDto qnaEditDto);

    int qnaUpdatePost(QnaEditDto qnaEditDto);

    int qnaDeletePost(Long id);

    void qnaUpdateViewCnt(Long id);
}
