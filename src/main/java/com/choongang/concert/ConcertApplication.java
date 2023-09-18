package com.choongang.concert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.choongang.concert.service.user.UserService;

//@ComponentScan(basePackages = UserService.class)
@SpringBootApplication
public class ConcertApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcertApplication.class, args);
    }

}