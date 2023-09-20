package com.choongang.concert.service.board;

import com.choongang.concert.dto.board.CreatePageDto;
import com.choongang.concert.dto.board.QnaDto;
import com.choongang.concert.dto.board.QnaEditDto;
import com.choongang.concert.repository.board.QnaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class QnaService {

	private final QnaMapper qnaMapper;


    public List<QnaDto> getQnaList(CreatePageDto createPageDto){

        return qnaMapper.qnaFindAll(createPageDto);
    }

    public int getQnaAllCount(CreatePageDto createPageDto){

        return qnaMapper.qnaAllCount(createPageDto);
    }

    public QnaDto findQnaDetail(Long id) {
    	
        return qnaMapper.qnaFindById(id);
    }

    public int qnaCreatePost(QnaEditDto qnaEditDto) {
    
        return qnaMapper.qnaCreatePost(qnaEditDto);
    }

    public int qnaEditPost(QnaEditDto qnaEditDto) {
        return qnaMapper.qnaUpdatePost(qnaEditDto);
    }

    public int qnaDeletePost(Long id) {
    	log.info("********************1111" + id);
        return qnaMapper.qnaDeletePost(id);
    }
    
	// 조회수 업그레이드 
	public QnaDto qnaFindViewPostById(Long id) {
		// 게시물 조회 
		QnaDto qd = qnaMapper.qnaFindById(id); 
		// 조회수 증가 
		qnaMapper.qnaUpdateViewCnt(id);
		return qd;	
	}
	
}
