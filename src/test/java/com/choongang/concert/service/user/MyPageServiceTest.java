package com.choongang.concert.service.user;

import com.choongang.concert.dto.mypage.MyPageDto;
import com.choongang.concert.repository.mypage.MyPageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        myPageMapper.findMyTicketList()
    }
}