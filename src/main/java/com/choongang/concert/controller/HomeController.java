package com.choongang.concert.controller;

import com.choongang.concert.dto.user.AdminDto;
import com.choongang.concert.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {


	private final UserService userService;

	@GetMapping("/")
	public String home() {


		return "index";
		
	}

	@GetMapping("/about")
	public String about(){

		return "about";
	}
	
	
}
