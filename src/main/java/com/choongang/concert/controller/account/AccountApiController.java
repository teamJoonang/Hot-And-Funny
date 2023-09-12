package com.choongang.concert.controller.account;

import com.choongang.concert.dto.user.*;
import com.choongang.concert.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AccountApiController {


    private final UserService userService;


    // 새로운 사용자 생성(회원가입 등록)
    @PostMapping("/signup")
    public ResponseEntity<String> saveUser(@RequestBody AddUserRequest userReq) {

        log.info("Post >> /user/signup | saveUser() 실행됨.");
        log.info("userReq::{}" , userReq);

        // 회원가입 양식 폼 중 하나라도 비어있다면 bad request 응답
        if(userReq.getLoginId().isEmpty() || userReq.getPassword().isEmpty() ||
                userReq.getRepeatPw().isEmpty() || userReq.getName().isEmpty() ||
                userReq.getNickname().isEmpty() || userReq.getTel().isEmpty() ||
                userReq.getAddress().isEmpty() || userReq.getAge() == null) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("양식을 모두 채워주세요.");
        }
        // 비밀번호 일치하지 않으면 bad request
        else if(!userReq.getPassword().equals(userReq.getRepeatPw())){

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("비빌번호가 일치하지 않습니다.");
        }

        // db단에 넣고 return 레코드 갯수를 받는다.
        int result = userService.saveUser(userReq);

        // 성공 레코드 갯수가 0 보다 많다면, 성공적.
        if(result > 0){
            log.info("새로운 사용자 회원가입 성공");
             // 201 , ok 반환 및 body에 회원가입 성공이라는 메시지 보냄.
             return ResponseEntity
                     .status(HttpStatus.CREATED)
                     .body("회원가입 성공");
         }
         else {
             log.info("db단까지의 접근 이루어졌으나 문제 발생.");
             // 실패한 경우 , 0보다 작은 경우.
             return ResponseEntity
                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("회원가입 실패.");
         }


    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // 사용자 로그인 (아직 검증 없음 , session 고급지게 뭔가 추가 필요 , password 암호화 필요 )//
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginReq , HttpSession session , HttpServletResponse res) throws Exception {

        log.info("Post >> /user/login | login() 실행됨.");
        log.info("loginReq::{}" , loginReq);

        // ajax로 받은 값 null 확인.
        if(loginReq.getLoginId().isEmpty() || loginReq.getPassword().isEmpty()){
            // null 이라면 bad request 보내라.
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("모든 양식을 채워주세요.");
        }

        // service를 이용해 db 조회해보고 userResponse정보 반환
        UserResponse result = userService.login(loginReq);


        if(result.getLoginId().equals(loginReq.getLoginId()) &&
                result.getPassword().equals(loginReq.getPassword())){
            // 세션에 id , loginId 를 넣어준다.
            log.info("session에 담을 id : " + result.getId());
            log.info("session에 담을 loginId : " + result.getLoginId());
            session.setAttribute("id" , result.getId());
            session.setAttribute("loginId" , result.getLoginId());

            Cookie idCookie = new Cookie("id" , String.valueOf(result.getId()));
            res.addCookie(idCookie);

            // 로그인 성공 success 200 요청 처리
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("로그인 성공");

        }
        else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 사용자.");
        }

    }

    // 사용자 이메일로 아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestBody FindEmailRequest findEmailReq){

        log.info("Post >> /user/findId | findUserId() 실행됨.");
        log.info("findEmailReq::{}" , findEmailReq);

        // null 체크 : Req의 loginid가 null 이라면 400 bad req 응답
        if(findEmailReq.getLoginId().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("모든 양식을 채워주세요");
        }

        // service를 통해 db 접근, loginId(email) 조회해서 객체 담기.
        UserResponse result = userService.findByEmail(findEmailReq);
        log.info("result::{}" , result);

        // 조회결과가 없을 경우 또는 요청 이메일과 db 이메일이 일치하지 않는 경우
        if(result == null){
            // 404 not found 응답 전송
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 회원");
        }
        else if(!result.getLoginId().equals(findEmailReq.getLoginId())) {
            // 404 not found 응답 전송
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 회원");
        }
        else {
            // 위의 식 통과의 경우, db 존재 및 일치 이므로 ok 200 응답
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("존재하는 회원");
        }

    }

    // 사용자 비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<String> findUserPw(@RequestBody FindPwRequest findPwReq , HttpSession session){

        log.info("Post >> /user/findPw | findUserPw() 실행됨.");
        log.info("findPwReq::{}" , findPwReq);

        // null 체크 : Req의 필드멤버 중 하나라도 null 이라면 400 bad req 응답
        if( findPwReq.getLoginId().isEmpty() ||
            findPwReq.getName().isEmpty() ||
            findPwReq.getTel().isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("모든 양식을 채워주세요");
        }

        // service를 통해 db 접근, 검색 조건은 req(dto)의 값들... 조회 해서 객체 담기.
        UserResponse result = userService.findByUser(findPwReq);

        // 조회결과가 없을 경우 또는 요청 이메일과 db 이메일이 일치하지 않는 경우
        if( result == null ||
                !result.getLoginId().equals(findPwReq.getLoginId()) ||
                !result.getName().equals(findPwReq.getName()) ||
                !result.getTel().equals(findPwReq.getTel()) ){

            // 404 not found 응답 전송
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 회원");
        }

        // 위의 식들을 통과할 경우, 혹시 세션이 존재하는가?
        if (session.isNew()){
            // 세션이 없다면 JSESSIONID 라는 세션이 생성되고 true가 반환되어 아래 로직 실행 예상 *******
            log.info("로그인한 정보가 없는 사용자 , 세션 추가됨.");

            // 세션에 pk를 넣어준다.
            session.setAttribute("id" , result.getId());
            // 세션에 loginid를 넣어준다.
            session.setAttribute("loginId" , result.getLoginId());

            // db 존재 및 일치이므로 ok 200 응답 보냄.
        } else {
            // JSESSIONID 세션은 존재하는 경우.
                session.setAttribute("id" , result.getId());
                session.setAttribute("loginId" , result.getLoginId());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("존재하는 회원");


    }


    // 비밀번호 재설정
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPwRequest resetPwReq , HttpSession session){

        log.info("Post >> /user/findPw | resetPassword() 실행됨.");
        log.info("ResetPwRequest::{}" , resetPwReq);
        log.info("POST단에서의 session = " + session.getAttribute("loginId"));

        // session에서 loginId 가져와서 dto에 넣어주기
        String loginId = (String)session.getAttribute("loginId");
        resetPwReq.setLoginId(loginId);

        // null 체크 : 넘어온 값이 제대로 들어있는가 확인, 없다면 해당 값에 따른 응답.
        if(resetPwReq.getPassword().isEmpty() || resetPwReq.getRepeatPassword().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("양식을 모두 채워주세요");
        }

        // 비밀번호가 일치하지 않는다면 bad request
        if(!resetPwReq.getPassword().equals(resetPwReq.getRepeatPassword())){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("비밀번호가 일치하지 않습니다.");
        }


        int result = userService.resetPassword(resetPwReq);
        log.info("result::{}" , result);

        if(result > 0){
            return ResponseEntity.
                    status(HttpStatus.OK)
                    .body("비밀번호 재설정됨.");
        }
        else {
            // 500 에러
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버에 문제 발생, 재설정 불가");
        }


    }


}