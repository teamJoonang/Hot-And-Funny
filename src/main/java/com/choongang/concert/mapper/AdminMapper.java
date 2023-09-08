package com.choongang.concert.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.PageDto;
import com.choongang.concert.dto.UserInfoDTO;

@Mapper
public interface AdminMapper {

	// 회원 전체 목록 조회
	public List<UserInfoDTO> getUserList(PageDto params);
	
	


    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int count(UserInfoDTO params);

}
