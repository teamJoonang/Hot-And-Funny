package com.choongang.concert.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choongang.concert.dto.PageDto;
import com.choongang.concert.dto.UserInfoDTO;
import com.choongang.concert.mapper.AdminMapper;

@Service
@Transactional
public class UserInfoService {

	private AdminMapper adminMapper;
	
	public UserInfoService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	public List<UserInfoDTO> getUserList(final PageDto params){
//		List<UserInfoDTO> userList = adminMapper.getUserList();
		
//		return userList;
		
		return adminMapper.getUserList(params);
	}
}
