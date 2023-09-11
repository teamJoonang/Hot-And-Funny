package com.choongang.concert.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
// 아이디/비번 찾기 , 이메일 찾기 dto
public class FindEmailRequest {

    private String loginId; // 이메일형식 id

}
