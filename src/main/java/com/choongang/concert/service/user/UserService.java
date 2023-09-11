package com.choongang.concert.service.user;

import com.choongang.concert.dto.user.*;
import com.choongang.concert.repository.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
@Slf4j
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
     * 사용자 비밀번호 찾기 (정보확인)
     * @param findPwReq - 컬럼과 일치하는 사용자 확인용 dto
     * @return UserResponse - 사용자 정보
     */
    public UserResponse findByUser(FindPwRequest findPwReq) {

        UserResponse userRes = userMapper.findByUser(findPwReq);
        return userRes;
    }

    /**
     * 사용자 비밀번호 재설정
     * @param resetPwReq - 비밀번호 와 비밀번호 재확인 , 사용자 번호
     * @return int - 성공적으로 레코드 변경? 0 < true
     */
    public int resetPassword(ResetPwRequest resetPwReq) {

        int result = userMapper.resetPassword(resetPwReq);
        return result;
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
