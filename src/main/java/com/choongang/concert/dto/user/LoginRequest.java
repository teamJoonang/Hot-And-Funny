package com.choongang.concert.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
// 로그인 양식 dto
public class LoginRequest {

    private String loginId;     // 로그인 아이디 - 이메일
    private String password;    // 비밀번호
    private String redirectURL; // 리다이렉트 url

}
