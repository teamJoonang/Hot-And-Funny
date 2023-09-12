package com.choongang.concert.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.admin.UserInfoDTO;
import com.choongang.concert.mapper.admin.AdminMapper;

@Service
public class StatService {
	
	private final AdminMapper adminMapper;
	
	public StatService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	

	
	//	SecondDonut차트에서 쓸 내용(연령대별 통계)
	public List<Integer> ageGroup(){
		return adminMapper.ageGroup();
	}
	
	//	Bar차트에서 쓸 내용(예매율)
	public List<Integer> reservationGroup(){
		return adminMapper.reservationGroup();
	}
}
