package com.choongang.concert.controller.mypage;

import com.choongang.concert.dto.mypage.MyTicketDto;
import com.choongang.concert.dto.user.AddUserRequest;
import com.choongang.concert.dto.user.UserResponse;
import com.choongang.concert.service.user.InputValidation;
import com.choongang.concert.service.user.MyPageService;
import com.choongang.concert.service.user.ResponseService;
import com.choongang.concert.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class MyPageApiController {

    private final UserService userService;
    private final InputValidation inputValidation;
    private final ResponseService responseService;
    private final MyPageService myPageService;



    @PostMapping("/nicknameModifyCk")
    public ResponseEntity<String> checkNicknameList(@RequestBody AddUserRequest userReq){
        // 실행로그 , dto의 nickname만 확인
        log.info("POST >> /user/nicknameModifyCk | checkNicknameList() 실행됨.");
        log.info("userReq::{} " , userReq);

        int result = userService.checkModifyNickname(userReq);
        log.info("result : " + result);

        if(result <= 0){
            return responseService.setSuccesResponse("사용가능한 별명");
        }
        else {
            return responseService.setBadResponse("별명 중복 , 다른 별명을 사용해주세요.");
        }

    }


    @PostMapping("/myinfo")
    public ResponseEntity<String> modifyUser(@RequestBody UserResponse userReq){

        log.info("Post >> /user/myinfo | modifyUser() 실행됨. ");
        log.info("userReq::{}" , userReq);
        // 만약 양식 중 하나가 비어 있다면
        if(inputValidation.isModifyEmpty(userReq)){
            return responseService.setBadResponse("양식을 모두 채워주세요.");
        }
        // db단에 update 시킨다.
        int result = userService.updateUser(userReq);
        log.info("result::{}" , result);

        if(result > 0){
            // update 성공 , 200 응답
            return responseService.setSuccesResponse("회원정보 수정 성공!");
        }
        else {
            log.info("db단까지의 접근 이루어졌으나 문제 발생");
            return responseService.setServerErrorResponse("회원정보 수정 실패.");
        }
    }

    @PostMapping("/myticket/api")
    @ResponseBody
    public String changeStatus(@RequestBody String uuid) {
        log.info("MY UUID = [{}]", uuid);
        myPageService.updateMyTicketStatus(uuid);

        MyTicketDto myTicketDto = myPageService.findMyTicketByUuid(uuid);
        log.info("My Ticket Dto = {}", myTicketDto.toString());
        log.info("Update Status -> {}", myTicketDto.getStatus().getValue());
        return myTicketDto.getStatus().getValue();
    }





}
