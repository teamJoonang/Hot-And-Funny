package com.choongang.concert.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.admin.Bar2Dto;
import com.choongang.concert.dto.admin.PageDto;
import com.choongang.concert.dto.admin.UserInfoDTO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {

	// 회원 전체 목록 조회
	public List<UserInfoDTO> getUserList(PageDto params);
	
	


    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int count(PageDto params);
    
    
    
    
    ///// statics.html에 필요한 연령별 인원수 카운트
//    public List<UserInfoDTO> getAgeList();
   
    //	SecondDonut 차트에서 쓸 내용 (연령대별 통계)
    public List<Integer> ageGroup();

    //	Bar 차트에서 쓸 내용 (예매율 통계)
    public List<Integer> reservationGroup();
    
	//	Bar2차트에서 쓸 내용(실시간 좌석 확인)
    public List<Bar2Dto> seatGroup();
   
}
