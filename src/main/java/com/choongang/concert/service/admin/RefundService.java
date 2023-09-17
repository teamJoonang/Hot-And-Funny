package com.choongang.concert.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.admin.RefundDto;
import com.choongang.concert.mapper.admin.AdminMapper;

@Service
public class RefundService {
	private AdminMapper adminMapper;
	
	public RefundService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	
	public List<RefundDto> getRefundList(){
		return adminMapper.getRefundList();
	}
	
	//	refund 응답
	public List<RefundDto> getResRefund(RefundDto refundDto){
		return adminMapper.getRefundData(refundDto);
	}
}
