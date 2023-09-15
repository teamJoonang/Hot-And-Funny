package com.choongang.concert.repository.mypage;

import com.choongang.concert.dto.mypage.MyPageDto;
import com.choongang.concert.dto.mypage.MyTicketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {



    List<MyPageDto> findMyQnaList(Long id);

    List<MyTicketDto> findMyTicketList(Long id);

    void updateMyTicketStatus(String uuid);

    MyTicketDto findMyTicketByUuid(String uuid);


}
