package com.choongang.concert.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.admin.PageDto;
import com.choongang.concert.dto.admin.UserInfoDTO;

@Mapper
public interface AdminMapper {

	// 회원 전체 목록 조회
	public List<UserInfoDTO> getUserList(PageDto params);
	
	


    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int count(PageDto params);
    
    
    
    
    // statics.html에 필요한 연령별 인원수 카운트
//    public List<UserInfoDTO> getAgeList();
    
    public int getTeen();
    public int getTwenty();
    public int getThirty();
    public int getForty();
    
    public List<Integer> ageGroup();

}
