package com.choongang.concert.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
// 비밀번호 재설정 페이지 dto
public class PwResetRequest {

    private String newPassword;
    private String repeatPassword;

}
