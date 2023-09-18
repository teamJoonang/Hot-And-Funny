package com.choongang.concert.service.admin;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choongang.concert.dto.admin.PageDto;
import com.choongang.concert.dto.admin.PaginationDTO;
import com.choongang.concert.dto.admin.PagingResponse;
import com.choongang.concert.dto.admin.UserInfoDTO;
import com.choongang.concert.dto.admin.UserInfoSearchDto;
import com.choongang.concert.mapper.admin.AdminMapper;

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
	


	
	
	
	
	
	 /**
     * 게시글 리스트 조회
     * @param params - search conditions
     * @return list & pagination information
     */
	
	
	public PagingResponse<UserInfoDTO> findAllUser(final PageDto params) {

        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = adminMapper.count(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        PaginationDTO paginationDto = new PaginationDTO(count, params);
        params.setPaginationDto(paginationDto);

        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<UserInfoDTO> list = adminMapper.getUserList(params);
        return new PagingResponse<>(list, paginationDto);
    }



}
