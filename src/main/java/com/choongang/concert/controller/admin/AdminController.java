package com.choongang.concert.controller.admin;



import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.concert.dto.AgeGroupDto;
import com.choongang.concert.dto.PageDto;
import com.choongang.concert.dto.PagingResponse;
import com.choongang.concert.dto.UserInfoDTO;
import com.choongang.concert.service.StatService;
import com.choongang.concert.service.UserInfoService;

@Controller
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	//DI 의존성 주입 생성자 메서드 주입방식(bean으로 등록 되어 있기 때문에 가능한 방법)
	private UserInfoService userInfoService;
	private StatService statService;
	
	public AdminController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	@Autowired
	public AdminController(StatService statService) {
		this.statService = statService;
	}

	
	
	@GetMapping("boardcontrol")
	public String boardControl() {
		return "admin/board_control";
	}
	
	@GetMapping("userinfo")
	public String userInfo(@ModelAttribute("params") final PageDto params, Model model) {
		log.info("회원목록요청");	//로그
		
//	    log.info("offset: {}", params.getOffset());
		
        PagingResponse<UserInfoDTO> response = userInfoService.findAllPost(params);
        model.addAttribute("response", response);		
		
		
//		log.info("회원목록 개수: " + adminList.size());
		return "admin/user_info";
	}
	
	@GetMapping("refund")
	public String refund() {
		return "admin/refund";
	}
	
	
	
	
	
	@GetMapping("statistics")	
	public String statistics(Model model) {
		
		int count = 4;
		
		List<Integer> ageGroup = statService.ageGroup();
		
		
		JSONArray ageGroupJsonArray = new JSONArray();
		
		for(int i = 0; count > i; i++) {
		    if (i < ageGroup.size()) {
		        ageGroupJsonArray.add(ageGroup.get(i));
		    }
		};
		
		model.addAttribute("ageGroupJsonArray", ageGroupJsonArray);
		
		log.info("여기야여기 : " + ageGroupJsonArray);
//		int teen = statService.getTeen();	
//		int twenty = statService.getTwenty();
//		int thirty = statService.getThirty();
//		int forty = statService.getForty();
		

		log.info("ageGroup : " + statService.ageGroup());
//		log.info("getTeen : " + statService.getTeen());
//		log.info("getTwety : " + statService.getTwenty());
//		log.info("getThirty : " + statService.getThirty());
//		log.info("getForty : " + statService.getForty());

		
		
		model.addAttribute("AgeGroup", ageGroup);
//		model.addAttribute("Teen", teen);
//		model.addAttribute("Twenty", twenty);
//		model.addAttribute("Thirty", thirty);
//		model.addAttribute("Forty", forty);
		
		return "admin/statistics";
	}
	
//	@GetMapping("/statistics-data")	
//	@ResponseBody // JSON 형태로 응답할 것임을 명시
//	public Map<String, Integer> getStatisticsData() {
//	    Map<String, Integer> statisticsData = new HashMap<>();
//	    statisticsData.put("Teen", statService.getTeen());
//	    statisticsData.put("Twenty", statService.getTwenty());
//	    statisticsData.put("Thirty", statService.getThirty());
//	    statisticsData.put("Forty", statService.getForty());
//	    
//	    return statisticsData;
//	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/stat/api")
	@ResponseBody
	public JSONArray stat() {

		int count = 4;

		
		JSONObject data = new JSONObject();
		
		
		
		List<Integer> ageGroup = statService.ageGroup();
		
		// js googleChart 로 리턴할 Json 객체
		JSONArray ageGroupJsonArray = new JSONArray();
		
		for(int i = 0; count > i; i++) {
		    if (i < ageGroup.size()) {
		        ageGroupJsonArray.add(ageGroup.get(i));
		    }
		};
		
		
		log.info("여기야여기 : " + ageGroupJsonArray);


		return ageGroupJsonArray;
		
		
		
	}
	
	
	
}
