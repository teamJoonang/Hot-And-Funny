package com.choongang.concert.service.user;

import com.choongang.concert.dto.user.AddUserRequest;
import com.choongang.concert.dto.user.FindPwRequest;
import com.choongang.concert.dto.user.ResetPwRequest;
import com.choongang.concert.dto.user.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


// 검증식으로 검증하여 true , false 돌려주자.
@Slf4j
@Service
public class InputValidation {

    // loginId(email) 검증식 : 대소문자 , 숫자 가능 1~30자 @ 소문자 , 숫자 , 마지막은 . 소문자
    private final String emailRegex = "^[A-Za-z0-9_]{1,30}@([a-z0-9_]+\\.)+[a-z]+$/";
    // 비밀번호 , 비밀번호 재확인
    // password & repeatPassword 검증식 : 대소문자 , 숫자 , 특수문자 무조건 하나 포함, 최소8~최대16자.
    private final String pwRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$";
    // 이름
    //  name 검증식 : 대소문자 , 한글 가능 , 최소 1~10자 , 숫자와 특수문자 제외.
    private final String nameRegex = "^[A-Za-z가-힝]{1,10}[^\\d$!%*#?&]$";
    // 별명
    // nickname 검증식 :  언더바 '_' 제외 , 특수문자 사용불가 , 최소 2 ~ 최대 10 글자.
    private final String nicknameRegex = "^[가-힣ㄱ-ㅎa-zA-z0-9]{2,10}[^$!%*#?&]$";
    // 연락처
    // tel 검증식 : 숫자만 가능 11글자.
    private final String telRegex = "\\d{11}";
    // 나이
    // age 검증식 : 숫자만 가능, 최소 1글자~3글자.
    private final String ageRegex = "\\d{1,3}";

    // 회원가입 input 양식 중 하나라도 비어있는가?
    public boolean isSignupEmpty(AddUserRequest userReq){
        if(userReq.getLoginId().isEmpty() || userReq.getPassword().isEmpty() ||
           userReq.getRepeatPw().isEmpty() || userReq.getName().isEmpty() ||
           userReq.getNickname().isEmpty() || userReq.getTel().isEmpty() ||
           userReq.getAddress().isEmpty() || userReq.getAge() == null ){
            log.info("회원가입 : 양식 중 하나, 빈 값 또는 null ");
            return true;
        }
        else {
            log.info("회원가입 : 양식에 값은 존재.");
            return false;
        }
    }

    // 회원가입 비밀번호와 비밀번호 재확인은 일치하는가?
    public boolean isPwSame(AddUserRequest userReq){
        if(userReq.getPassword().equals(userReq.getRepeatPw())){
            log.info("회원가입 : 비밀번호와 비밀번호 재확인 통과.");
            return false;   // 값이 일치하니 if문이 실행되지 않도록 false 반환
        }
        else {
            log.info("회원가입 : 비밀번호와 비밀번호 재확인 불일치");
            return true;    // 일치하지 않으니 if문 실행하도록 true 반환
        }
    }

    public boolean isModifyEmpty(UserResponse userReq){
        if(userReq.getName().isEmpty() || userReq.getNickname().isEmpty() ||
           userReq.getTel().isEmpty() || userReq.getAddress().isEmpty() ||
           userReq.getAge() == null ){
            log.info("회원정보 수정 : 양식 중 하나, 빈 값 또는 null ");
            return true;
        }
        else {
            log.info("회원정보 : 양식에 값은 존재.");
            return false;
        }
    }

    // 사용자 비밀번호 찾기 양식에 값이 존재하나? : 이메일(loginId) , 이름 , 연락처
    public boolean isFindPwEmpty(FindPwRequest findPwReq){
        if (findPwReq.getLoginId().isEmpty() || findPwReq.getName().isEmpty() || findPwReq.getTel().isEmpty()){
            log.info("user/find : 양식 중 하나가 빈값 또는 null");
            return true;
        }
        else {
            log.info("user/find : 양식에 값은 존재. ");
            return false;
        }
    }

    // 사용자 비밀번호 재설정 양식에 값이 존재하나? : 비밀번호 , 비밀번호 재확인
    public boolean isResetPwEmpty(ResetPwRequest resetPwReq){
        if(resetPwReq.getPassword().isEmpty() || resetPwReq.getRepeatPassword().isEmpty()){
            log.info("user/reset : 빈값 또는 null 존재");
            return true;
        }
        else {
            log.info("user/reset : 양식에 값은 존재.");
            return false;
        }
    }

    public boolean validateEmail(String email) {
        boolean result = Pattern.matches(emailRegex, email);
        log.info("이메일 검증 통과");
        return result;
    }

    public boolean validatePassword(String password) {
        boolean result = Pattern.matches(pwRegex, password);
        log.info("패스워드 검증 통과");
        return result;
    }

    public boolean validateName(String name) {
        boolean result = Pattern.matches(nameRegex, name);
        log.info("이름 검증 통과");
        return result;
    }

    public boolean validateNickname(String nickname) {
        boolean result = Pattern.matches(nicknameRegex, nickname);
        log.info("별명 검증 통과");
        return result;
    }

    public boolean validateTel(String tel) {
        boolean result = Pattern.matches(telRegex, tel);
        log.info("연락처 검증 통과");
        return result;
    }

    public boolean validateAge(String age) {
        boolean result = Pattern.matches(ageRegex, age);
        log.info("나이 검증 통과");
        return result;
    }






}
