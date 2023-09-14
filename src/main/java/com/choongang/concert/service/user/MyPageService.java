package com.choongang.concert.service.user;


import com.choongang.concert.dto.mypage.MyPageDto;
import com.choongang.concert.repository.mypage.MyPageMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public List<MyPageDto> findMyQna(Long id){


        return myPageMapper.findMyQnaList(id);

    }
}
