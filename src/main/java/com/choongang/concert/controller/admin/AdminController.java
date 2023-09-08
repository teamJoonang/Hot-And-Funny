package com.choongang.concert.controller.admin;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.choongang.concert.dto.PageDto;
import com.choongang.concert.dto.UserInfoDTO;
import com.choongang.concert.service.UserInfoService;

@Controller
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	//DI 의존성 주입 생성자 메서드 주입방식(bean으로 등록 되어 있기 때문에 가능한 방법)
	private UserInfoService adminService;
	
	public AdminController(UserInfoService adminService) {
		this.adminService = adminService;
	}

	
	
	@GetMapping("boardcontrol")
	public String boardControl() {
		return "admin/board_control";
	}
	
	@GetMapping("userinfo")
	public String userInfo(@ModelAttribute("params") final PageDto params, Model model) {
		log.info("회원목록요청");	//로그
		
	    log.info("offset: {}", params.getOffset());
		
		List<UserInfoDTO> adminList = adminService.getUserList(params);
		
		
		model.addAttribute("title", "회원목록조회");
		model.addAttribute("adminList", adminList);
		
		
		
		log.info("회원목록 개수: " + adminList.size());
		return "admin/user_info";
	}
	
	@GetMapping("refund")
	public String refund() {
		return "admin/refund";
	}
	
	@GetMapping("statistics")
	public String statistics() {
		return "admin/statistics";
	}
}
