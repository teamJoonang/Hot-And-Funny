package com.choongang.concert.repository.user;

import com.choongang.concert.dto.user.*;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    /**
     * 회원가입 insert
     * @param userReq - 사용자 정보 전송
     * return int - 등록된 레코드 수
     * 성공 value > 0 >= value 실패
     */
    int saveUser(AddUserRequest userReq);

//    /**
//     * 사용자 정보조회
//     * @param id - pk
//     * @return 사용자 정보
//     */
//    UserResponse findByUserId(Long id);

    /**
     * 사용자 로그인 정보확인
     * @param loginReq - 로그인 요청
     * @return UserResponse - 사용자 정보
     */
    UserResponse login (LoginRequest loginReq);


    /**
     * 사용자 이메일 찾기 (정보확인)
     * @param findEmailReq - 이메일 조회
     * @return UserResponse - 사용자 정보
     */
    UserResponse findByEmail(FindEmailRequest findEmailReq);

    /**
     * 사용자 비밀번호 찾기 (정보 확인)
     * @param findPwReq - 컬럼과 일치하는 사용자 확인용 dto
     * @return UserResponse - 사용자 정보
     */
    UserResponse findByUser(FindPwRequest findPwReq);

    /**
     *  사용자 비밀번호 재설정
     * @param resetPwReq - 비밀번호와 비밀번호 재확인 값
     * @return int - 성공한 레코드 갯수 1
     */
    int resetPassword(ResetPwRequest resetPwReq);


//    /**
//     * 사용자 정보 수정
//     * @param params -사용자 정보
//     */
//    void update(UserDTO params);
//
//    /**
//     * 사용자 삭제(탈퇴)
//     * @param id - pk
//     */
//    void deleteById(Long id);



}
