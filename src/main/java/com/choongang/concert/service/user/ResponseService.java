package com.choongang.concert.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResponseService {
    // return 응답요청, 코드 중복과 가독성 하락 임시방편
    // 200 ok 응답
    public ResponseEntity<String> setSuccesResponse(String msg){
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }
    // 201 created 응답
    public ResponseEntity<String> setCreatedResponse(String msg){
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
    // 400 bad request 응답
    public ResponseEntity<String> setBadResponse(String msg){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
    // 404 not found 응답
    public ResponseEntity<String> setNotFoundResponse(String msg){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }
    // 500 server error 응답
    public ResponseEntity<String> setServerErrorResponse(String msg){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
    }
}
