package com.choongang.concert.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choongang.concert.dto.admin.QnaPostDto;
import com.choongang.concert.dto.admin.TotalPostDto;
import com.choongang.concert.mapper.admin.AdminMapper;

@Service
public class BoardControlService {
	
	private AdminMapper adminMapper;
	
	public BoardControlService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	
	public List<TotalPostDto> totalPostList(){
		return adminMapper.getTotalList();
	}
	
	public List<QnaPostDto> qnaPostList(){
		return adminMapper.getQnaList();
	}
	
	
	///////////////////	게시물 삭제
	///////////전체게시물관리 삭제
	@Transactional
	public void deleteTotal(List<Long> totalDataForm) {
		
		adminMapper.deleteTotal(totalDataForm);
	}
	
	@Transactional
	public void resTotalDelete(List<Long> totalDataForm) {
		
		adminMapper.resTotalDelete(totalDataForm);
	}
//	@Transactional
//	public void updateQna(QnaPostDto qnaPostDto) {adminMapper.updateQna(qnaPostDto);}
///////////////////	qna 게시물 삭제
	//	서버 동작
	@Transactional
	public void deleteQna(List<Long> dataForm) {
		
		adminMapper.deleteQna(dataForm);
	}
	
	//	디비까지 전달해주는
	@Transactional
	public void resDelete(List<Long> dataForm) {
		
		adminMapper.resDelete(dataForm);
	}
	
	
	
}
