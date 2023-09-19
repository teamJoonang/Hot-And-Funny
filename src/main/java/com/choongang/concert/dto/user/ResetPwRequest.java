package com.choongang.concert.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
// 비밀번호 재설정 진행 dto
public class ResetPwRequest {

//    private Long id;              // 사용자 번호 pk
    private String loginId;         // 사용자 아이디
    private String password;        // 새로운 비밀번호
    private String repeatPassword;  // 새로운 비밀번호 재확인

}
