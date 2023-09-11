package com.choongang.concert.service.user;

import com.choongang.concert.dto.user.AddUserRequest;
import com.choongang.concert.dto.user.FindEmailRequest;
import com.choongang.concert.dto.user.LoginRequest;
import com.choongang.concert.dto.user.UserResponse;
import com.choongang.concert.repository.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    /**
     * @param req - 사용자 가입 정보
     * @return int - 성공 레코드 > 0 true
     */
    public int saveUser(AddUserRequest req){

        int result = userMapper.saveUser(req);
        return result;
    }

    /**
     * 로그인 검증 db 조회
     * @param loginReq - 로그인 정보
     * @return  - 조회된 레코드 > 0 true
     */
    public UserResponse login(LoginRequest loginReq){

        UserResponse userRes  = userMapper.login(loginReq);
        return  userRes;
    }

    /**
     * 사용자 이메일 찾기 (정보확인)
     * @param findEmailReq - 이메일 조회
     * @return UserResponse - 사용자 정보
     */
    public UserResponse findByEmail(FindEmailRequest findEmailReq){

        UserResponse userRes = userMapper.findByEmail(findEmailReq);
        return userRes;
    }


    /**
     * 사용자 회원정보 조회
     * @param id - PK
     * @return 사용자 회원정보
     */
//    public UserInfoResponse findByUserId(Long id){
//
//        return userMapper.findByUserId(id);
//    }

}
