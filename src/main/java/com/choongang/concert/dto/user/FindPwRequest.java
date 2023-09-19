package com.choongang.concert.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
// 아이디 / 비번찾기 , 비번 찾기 dto
public class FindPwRequest {

    private String loginId; // 이메일 형식 id
    private String name;    // 이름
    private String tel;     // 전화번호

}
