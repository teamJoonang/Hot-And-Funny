package com.choongang.concert.controller.account;

import com.choongang.concert.dto.user.*;
import com.choongang.concert.service.user.InputValidation;
import com.choongang.concert.service.user.RegisterMail;
import com.choongang.concert.service.user.ResponseService;
import com.choongang.concert.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.SessionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AccountApiController {


    private final UserService userService;
    private final InputValidation inputValidation;
    private final ResponseService responseService;
    private final RegisterMail registerMail;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/mailConfirm")
    public ResponseEntity<String> mailConfirm(@RequestBody String email) throws Exception{
        log.info("email = " + email);
        String code = registerMail.sendSimpleMessage(email);
        log.info("사용자에게 발송한 인증코드 ==> " + code );

        return responseService.setSuccesResponse(code);
    }

    // 아이디 중복 체크 확인 (회원가입 등록)
    @PostMapping("/IdCheck")
    public ResponseEntity<String> checkId(@RequestBody AddUserRequest userReq){
        // 실행로그 , dto의 LoginId만 확인.
        log.info("POST >> /user/IdCheck | checkId() 실행됨.");
        log.info("userReq.getLoginId : " + userReq.getLoginId());

        int result = userService.checkId(userReq.getLoginId());
        log.info("result : " + result);

        if(result <= 0){
            return responseService.setSuccesResponse("사용가능한 아이디.");
        }
        else {
            return responseService.setBadResponse("아이디 중복 , 다른 아이디를 사용해주세요.");
        }
    }

    // 별명 중복체크 (회원가입 등록)
    @PostMapping("/nicknameCheck")
    public ResponseEntity<String> CheckNickname(@RequestBody AddUserRequest userReq){
        // 실행로그 , dto의 nickname만 확인
        log.info("POST >> /user/nicknameCheck | checkNickname() 실행됨.");
        log.info("userReq.getNickname() : " + userReq.getNickname());

        int result = userService.checkNickname(userReq.getNickname());
        log.info("result : " + result );

        if(result <= 0){
            return responseService.setSuccesResponse("사용가능한 별명");
        }
        else {
            return responseService.setBadResponse("별명 중복 , 다른 별명을 사용해주세요.");
        }

    }

    // 새로운 사용자 생성(회원가입 등록)
    @PostMapping("/signup")
    public ResponseEntity<String> saveUser(@RequestBody AddUserRequest userReq) {
        // 실행로그 , dto 인자 확인
        log.info("Post >> /user/signup | saveUser() 실행됨.");
        log.info("userReq::{}" , userReq);
        // 회원가입 양식 폼 중 하나라도 비어있다면 bad request 응답
        if(inputValidation.isSignupEmpty(userReq)) {
            return responseService.setBadResponse("양식을 모두 채워주세요.");
        }
        if(inputValidation.isPwSame(userReq)){
            return responseService.setBadResponse("비밀번호가 일치하지 않습니다.");
        }

        log.info("회원가입 사용자 비밀번호 : " + userReq.getPassword());
        String encodedPw = bCryptPasswordEncoder.encode(userReq.getPassword());
        log.info("암호화된 회원가입 사용자 비밀번호 : " + encodedPw);
        userReq.setPassword(encodedPw);

        // db단에 넣고 return 레코드 갯수를 받는다.
        int result = 0;
        try {
            result = userService.saveUser(userReq);
        }
        catch (Exception e) {
            log.info("db에 회원가입 정보 insert 실패.");
            throw new RuntimeException(e);
        }
        // 성공 레코드 갯수가 0 보다 많다면, 성공적.
        if(result > 0){
            log.info("새로운 사용자 회원가입 성공");
             // 201 , ok 반환 및 body에 회원가입 성공이라는 메시지 보냄.
             return responseService.setCreatedResponse("회원가입 성공!");
         }
         else {
             log.info("db단까지의 접근 이루어졌으나 문제 발생.");
             // 실패한 경우 , 0보다 작은 경우.
             return responseService.setServerErrorResponse("회원가입 실패.");
         }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest loginReq , HttpServletRequest req, HttpServletResponse res) throws SessionException, IOException {
        log.info("Post >> /user/login | login() 실행됨.");
        log.info("loginReq::{}" , loginReq);

        // ajax로 받은 값 null 확인 , null 이라면 bad request 보내라.
        if(loginReq.getLoginId().isEmpty() || loginReq.getPassword().isEmpty()){
            return responseService.setBadResponse("모든 양식을 채워주세요.");
        }

        // service를 이용해 db 조회해보고 userResponse정보 반환
        UserResponse result = userService.login(loginReq);

        // db의 loginId,pw 와 dto의 loginId,pw가 일치하면...
        if(result.getLoginId().equals(loginReq.getLoginId()) &&
                bCryptPasswordEncoder.matches(loginReq.getPassword() , result.getPassword())){

            HttpSession session = req.getSession();
            // 세션에 id , loginId 를 넣어준다.
            log.info("session에 담을 id : " + result.getId());
            log.info("session에 담을 loginId : " + result.getLoginId());
            log.info("session에 담을 nickname : " + result.getNickname());
            session.setAttribute("id" , result.getId());
            session.setAttribute("loginId" , result.getLoginId());
            session.setAttribute("nickname" ,  result.getNickname());
//            if(loginReq.getRedirectURL() != null && !loginReq.getRedirectURL().isEmpty()){
//                return responseService.setSuccesResponse(loginReq.getRedirectURL());
//            }
            return responseService.setSuccesResponse("로그인 성공");
        }
        else {
            return responseService.setNotFoundResponse("존재하지 않는 사용자.");
        }

    }

    // 사용자 이메일로 아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestBody FindEmailRequest findEmailReq){

        log.info("Post >> /user/findId | findUserId() 실행됨.");
        log.info("findEmailReq::{}" , findEmailReq);

        // null 체크 : Req의 loginid가 null 이라면 400 bad req 응답
        if(findEmailReq.getLoginId().isEmpty()){
            return responseService.setBadResponse("모든 양식을 채워주세요");
        }
        // service를 통해 db 접근, loginId(email) 조회해서 객체 담기.
        UserResponse result = userService.findByEmail(findEmailReq);
        log.info("result::{}" , result);

        // 조회결과가 없을 경우 또는 요청 이메일과 db 이메일이 일치하지 않는 경우 , 404 not found 응답 전송
        if(result == null || !result.getLoginId().equals(findEmailReq.getLoginId())){
            return responseService.setNotFoundResponse("존재하지 않는 회원");
        }else {
            // 위의 식 통과의 경우, db 존재 및 일치 이므로 ok 200 응답
            return responseService.setSuccesResponse("존재하는 회원");
        }

    }

    // 사용자 비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<String> findUserPw(@RequestBody FindPwRequest findPwReq , HttpSession session){

        log.info("Post >> /user/findPw | findUserPw() 실행됨.");
        log.info("findPwReq::{}" , findPwReq);
        // null 체크 : Req의 필드멤버 중 하나라도 null 이라면 400 bad req 응답
        if(inputValidation.isFindPwEmpty(findPwReq)) {
            return responseService.setBadResponse("모든 양식을 채워주세요");
        }
        // service를 통해 db 접근, 검색 조건은 req(dto)의 값들... 조회 해서 객체 담기.
        UserResponse result = userService.findByUser(findPwReq);
        // 조회결과가 없을 경우 또는 요청 이메일과 db 이메일이 일치하지 않는 경우
        if( result == null ||
           !result.getLoginId().equals(findPwReq.getLoginId()) ||
           !result.getName().equals(findPwReq.getName()) ||
           !result.getTel().equals(findPwReq.getTel()) ){
            // 404 not found 응답 전송
            return responseService.setBadResponse("존재하지 않는 회원");
        }
        // 위의 식들을 통과할 경우, 혹시 세션이 존재하는가?
        if (session.isNew()){
            // 세션이 없다면 JSESSIONID 라는 세션이 생성되고 true가 반환되어 아래 로직 실행 예상 *******
            log.info("로그인한 정보가 없는 사용자 , 세션 추가됨.");
            // 세션에 pk를 넣어준다.
            session.setAttribute("id" , result.getId());
            // 세션에 loginid를 넣어준다.
            session.setAttribute("loginId" , result.getLoginId());
        }
        else {
            // JSESSIONID 세션은 존재하는 경우.
                session.setAttribute("id" , result.getId());
                session.setAttribute("loginId" , result.getLoginId());
        }
        // db 존재 및 일치이므로 ok 200 응답 보냄.
        return responseService.setSuccesResponse("존재하는 회원");
    }

    // 비밀번호 재설정
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPwRequest resetPwReq , HttpSession session){

        log.info("Post >> /user/reset | resetPassword() 실행됨.");
        log.info("ResetPwRequest::{}" , resetPwReq);
        log.info("POST단에서의 session.loginId = " + session.getAttribute("loginId"));

        // session에서 loginId 가져와서 dto에 넣어주기
        String loginId = (String)session.getAttribute("loginId");
        resetPwReq.setLoginId(loginId);

        // null 체크 : 넘어온 값이 제대로 들어있는가 확인, 없다면 해당 값에 따른 응답.
        if(inputValidation.isResetPwEmpty(resetPwReq)){
            return responseService.setBadResponse("양식을 모두 채워주세요");
        }
        // 비밀번호가 일치하지 않는다면 bad request
        if(!resetPwReq.getPassword().equals(resetPwReq.getRepeatPassword())){
            return responseService.setBadResponse("비밀번호가 일치하지 않습니다.");
        }

        String encodePw = bCryptPasswordEncoder.encode(resetPwReq.getPassword());
        resetPwReq.setPassword(encodePw);

        // db에 새로운 비밀번호 넣고 성공한 레코드 수 반환.
        int result = userService.resetPassword(resetPwReq);
        log.info("result::{}" , result);

        if(result > 0){
            return responseService.setSuccesResponse("비밀번호 재설정됨.");
        }
        else {
            return responseService.setServerErrorResponse("서버에 문제 발생, 재설정 불가");
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request , HttpServletResponse response) throws ServletException {

        // 세션이 만약 있고 세션 안에 loginId라는 속성도 갖고 있다면 index 페이지로 리다이렉트.
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("loginId") != null){
            session.removeAttribute("loginId");
            session.removeAttribute("id");
            session.removeAttribute("nickname");
            session.setMaxInactiveInterval(0);
            session.invalidate();
        }

        if(request.getCookies() != null ){
            for (Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("JSESSIONID")){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return "redirect:/";
    }




}