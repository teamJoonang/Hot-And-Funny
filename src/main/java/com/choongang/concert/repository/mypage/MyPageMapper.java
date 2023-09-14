package com.choongang.concert.repository.mypage;

import com.choongang.concert.dto.mypage.MyPageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {



    List<MyPageDto> findMyQnaList(Long id);
}
