package com.choongang.concert.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
// 회원가입 양식 폼 dto
public class AddUserRequest {

    private Long id;            // pk
    private String loginId;     // email
    private String password;    // 비밀번호
    private String repeatPw;    // 비밀번호 확인
    private String name;        // 이름
    private String nickname;    // 별명
    private String address;     // 주소
    private Integer age;        // 나이
    private String tel;         // 연락처
    private Boolean Gender;     // 성별
    /*  성별
        js에서의 값 : true = 남자 | false = 여자
        db에서의 값 : true = 남자 = 1 | false = 여자 = 0
     */

}


