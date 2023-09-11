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
    @NotBlank
    private String loginId;     // email
    @NotBlank
    private String password;    // 비밀번호
    @NotBlank
    private String repeatPw;    // 비밀번호 확인
    @NotBlank
    private String name;        // 이름
    @NotBlank
    private String nickname;    // 별명
    @NotBlank
    private String address;     // 주소
    @NotBlank
    private Integer age;        // 나이
    @NotBlank
    private String tel;         // 연락처

}


