package com.choongang.concert.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
// 서버의 응답용 사용자 dto
public class UserResponse {

    private Long id;            // pk
    private String loginId;     // email
    private String password;    // 비밀번호
    private String repeatPw;    // 비밀번호 확인
    private String name;        // 이름
    private String nickname;    // 별명
    private String address;     // 주소
    private Integer age;        // 나이
    private String tel;         // 연락처

}
