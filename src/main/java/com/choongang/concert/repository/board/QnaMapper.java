package com.choongang.concert.repository.board;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.QnaDto;
import com.choongang.concert.dto.board.QnaEditDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
