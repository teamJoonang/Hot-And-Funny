package com.choongang.concert.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choongang.concert.dto.admin.QnaPostDto;
import com.choongang.concert.mapper.admin.AdminMapper;

@Service
public class BoardControlService {
	
	private AdminMapper adminMapper;
	
	public BoardControlService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	
	public List<QnaPostDto> qnaPostList(){
		return adminMapper.getQnaList();
	}
	
	@Transactional
	public void updateQna(QnaPostDto qnaPostDto) {adminMapper.updateQna(qnaPostDto);}
	
	@Transactional
	public void deleteQna(List<Long> ids) {
		
		adminMapper.deleteQna(ids);
	}
}
