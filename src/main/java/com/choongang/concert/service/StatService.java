package com.choongang.concert.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.concert.dto.UserInfoDTO;
import com.choongang.concert.mapper.AdminMapper;

@Service
public class StatService {
	
	private final AdminMapper adminMapper;
	
	public StatService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	
	
	

	
	
	public List<Integer> ageGroup(){
		return adminMapper.ageGroup();
	}
}
