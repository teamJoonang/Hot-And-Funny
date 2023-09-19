package com.choongang.concert.dto.admin;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PagingResponse<T> {

    private int id;
    private String loginId;
    private String name;
    private String nickname;
    private String address;
    private String gender;
    private String tel;  
	
    private List<T> list = new ArrayList<>();
    private PaginationDTO paginationDto;

    public PagingResponse(List<T> list, PaginationDTO paginationDto) {
        this.list.addAll(list);
        this.paginationDto = paginationDto;
    }

}