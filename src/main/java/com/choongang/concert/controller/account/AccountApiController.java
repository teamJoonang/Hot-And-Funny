package com.choongang.concert.controller.account;

import com.choongang.concert.dto.user.AddUserRequest;
import com.choongang.concert.dto.user.FindEmailRequest;
import com.choongang.concert.dto.user.LoginRequest;
import com.choongang.concert.dto.user.UserResponse;
import com.choongang.concert.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class AccountApiController {

    private final UserService userService;

    //    새로운 사용자 생성(회원가입 등록)
    @PostMapping("/signup")
    public ResponseEntity<String> saveUser(@RequestBody AddUserRequest userReq) {

        log.info("Post >> /user/signup | saveUser() 실행됨.");
        log.info("userReq::{}" , userReq);

        // db단에 넣고 return 레코드 갯수를 받는다.
        int result = userService.saveUser(userReq);

        // 성공 레코드 갯수가 0 보다 많다면, 성공적.
        if(result > 0){
             // 201 , ok 반환 및 body에 회원가입 성공이라는 메시지 보냄.
             return ResponseEntity
                     .status(HttpStatus.CREATED)
                     .body("회원가입 성공");
         }
         else {
             // 실패한 경우 , 0보다 작은 경우.
             return ResponseEntity
                     .status(HttpStatus.BAD_REQUEST)
                     .body("실패.");
         }


    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // 사용자 로그인 (아직 검증 없음 , session 고급지게 뭔가 추가 필요 , password 암호화 필요 )//
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginReq , HttpSession session) throws Exception {

        log.info("Post >> /user/login | login() 실행됨.");
        log.info("loginReq::{}" , loginReq);

        // ajax로 받은 값 null 확인.
        if(loginReq.getLoginId() == null || loginReq.getPassword() == null){
            // null 이라면 bad request 보내라.
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("모든 양식을 채워주세요.");
        }

        // service를 이용해 db 조회해보고 userResponse정보 반환
        UserResponse result = userService.login(loginReq);

        // 일단 세션에 pk를 그대로 갖다 박는다...
        log.info("session에 담을 id : " + result.getId());
        session.setAttribute("id" , result.getId());

        // 로그인 성공 success 200 요청 처리
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("로그인 성공");
    }

    // 사용자 이메일로 아이디 찾기
    @PostMapping("/find")
    public ResponseEntity<String> findUserId(@RequestBody FindEmailRequest findEmailReq){

        log.info("Post >> /user/findId | findUserId() 실행됨.");
        log.info("findEmailReq::{}" , findEmailReq);

        if(findEmailReq.getLoginId() == null){

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("모든 양식을 채워주세요");
        }

        UserResponse result = userService.findByEmail(findEmailReq);

        if(result.getLoginId() == null || result.getLoginId() != findEmailReq.getLoginId()){

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 회원");
        }else {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("존재하는 회원");
        }

    }

}
