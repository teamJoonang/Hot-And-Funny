package com.choongang.concert.service.user;

import com.choongang.concert.dto.mypage.MyPageDto;
import com.choongang.concert.dto.mypage.MyTicketDto;
import com.choongang.concert.dto.mypage.Status;
import com.choongang.concert.repository.mypage.MyPageMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
class MyPageServiceTest {

    @Autowired
    private MyPageMapper myPageMapper;


    @Test
    void getMyQnaList() {

        List<MyPageDto> myQnaList = myPageMapper.findMyQnaList(4L);

        myQnaList.iterator().forEachRemaining(q -> System.out.println(q.toString()));
    }

    @Test
    void getMyTicketList() {

        List<MyTicketDto> myTicketList = myPageMapper.findMyTicketList(2L);

//        myTicketList.iterator().forEachRemaining(t -> System.out.println(t.toString()));
//        myTicketList.iterator().forEachRemaining(t -> System.out.println(t.getStatus().getValue()));

        MyTicketDto myTicketDto = myPageMapper.findMyTicketByUuid("9d22b019-52dd-11ee-9f83-8cb0e9029004");
        System.out.println(myTicketDto.toString());
    }


}