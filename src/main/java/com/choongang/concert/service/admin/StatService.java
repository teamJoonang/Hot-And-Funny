package com.choongang.concert.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.admin.Bar2Dto;
import com.choongang.concert.repository.admin.AdminMapper;

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
	
	//	Bar2차트에서 쓸 내용(실시간 좌석 확인)
	public List<Bar2Dto> seatGroup(){
		return adminMapper.seatGroup();
	}
	
	//	Donut차트에서 쓸 내용(성비별 통계)
	public List<Integer> genderGroup(){
		return adminMapper.genderGroup();
	}
	
	//	Area차트에서 쓸 내용 (매출 현황)
	public List<Integer> areaGroup(){
		return adminMapper.areaGroup();
	}
}
