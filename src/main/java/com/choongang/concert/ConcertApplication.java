package com.choongang.concert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

//@ComponentScan(basePackageClasses = BCryptPasswordEncoder.class)
//@ComponentScan(basePackageClasses = JavaMailSender.class)
@SpringBootApplication
public class ConcertApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcertApplication.class, args);
	}

}
