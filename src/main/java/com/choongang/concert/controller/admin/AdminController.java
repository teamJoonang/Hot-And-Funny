package com.choongang.concert.controller.admin;



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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.concert.dto.admin.Bar2Dto;
import com.choongang.concert.dto.admin.PageDto;
import com.choongang.concert.dto.admin.PagingResponse;
import com.choongang.concert.dto.admin.QnaPostDto;
import com.choongang.concert.dto.admin.UserInfoDTO;
import com.choongang.concert.service.admin.BoardControlService;
import com.choongang.concert.service.admin.StatService;
import com.choongang.concert.service.admin.UserInfoService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	//DI 의존성 주입 생성자 메서드 주입방식(bean으로 등록 되어 있기 때문에 가능한 방법)
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private StatService statService;
	@Autowired
	private BoardControlService boardControlService;
	
//	public AdminController(UserInfoService userInfoService) {
//		this.userInfoService = userInfoService;
//	}
//	@Autowired
//	public AdminController(StatService statService) {
//		this.statService = statService;
//	}

	
	
	@GetMapping("/boardcontrol")
	public String boardControl(Model model) {
		List<QnaPostDto> qnaPostList = boardControlService.qnaPostList();
		model.addAttribute("qnaPostList", qnaPostList);
		
		log.info("qnaPostList : " + qnaPostList);
		
		return "admin/board_control";
	}
	
	


	@PostMapping("/boardcontrol/delete")
	public String deleteQnaPost(@RequestBody List<Long> ids) {
		System.out.println(ids);
	    boardControlService.deleteQna(ids);
	    return "redirect:/boardcontrol";
	}
	
	
	
	
	@GetMapping("/userinfo")
	public String userInfo(@ModelAttribute("params") final PageDto params, Model model) {
		log.info("회원목록요청");	//로그
//	    log.info("offset: {}", params.getOffset());
		
        PagingResponse<UserInfoDTO> response = userInfoService.findAllUser(params);
        model.addAttribute("response", response);
        for (UserInfoDTO userInfoDTO : response.getList()) {
            String gender = userInfoDTO.getGender();
            if (gender.equals(0)) {
                userInfoDTO.setGender("여성");
            } else {
                userInfoDTO.setGender("남성");
            }
        };

//        log.info("response : " + response);
        model.addAttribute("pageDto", params); // "pageDto"는 Thymeleaf에서 사용할 이름
		log.info("파람::{}" , params);
//		log.info("회원목록 개수: " + adminList.size());
		return "admin/user_info";
	}
	
	@GetMapping("/refund")
	public String refund() {
		return "admin/refund";
	}
	
	
	
	
	
	@GetMapping("/statistics")
	public String statistics() {
		
//		int count = 4;
//		
//		List<Integer> ageGroup = statService.ageGroup();
//		
//		
//		JSONArray ageGroupJsonArray = new JSONArray();
//		
//		for(int i = 0; count > i; i++) {
//		    if (i < ageGroup.size()) {
//		        ageGroupJsonArray.add(ageGroup.get(i));
//		    }
//		};
//		
//		model.addAttribute("ageGroupJsonArray", ageGroupJsonArray);
//		
//		log.info("여기야여기 : " + ageGroupJsonArray);
//		int teen = statService.getTeen();	
//		int twenty = statService.getTwenty();
//		int thirty = statService.getThirty();
//		int forty = statService.getForty();
		

//		log.info("ageGroup : " + statService.ageGroup());
//		log.info("getTeen : " + statService.getTeen());
//		log.info("getTwety : " + statService.getTwenty());
//		log.info("getThirty : " + statService.getThirty());
//		log.info("getForty : " + statService.getForty());

		
		
//		model.addAttribute("AgeGroup", ageGroup);
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
	
	
	//	secondDonut차트(연령대별 차트)에서 쓸 디비에서 가져온 데이터 JSON 데이터로 전송
	@GetMapping("/stat/seconddonut")
	@ResponseBody
	public JSONArray seconddonut() {

		int count = 4;

		
		
		
		//SecondDonut차트 (고객통계 중 연령대별 통계 차트)
		List<Integer> ageGroup = statService.ageGroup();
		
		// js googleChart 로 리턴할 Json 객체
		JSONArray ageGroupJsonArray = new JSONArray();
		
		
		for(int i = 0; count > i; i++) {
		    if (i < ageGroup.size()) {
		        ageGroupJsonArray.add(ageGroup.get(i));
		    }
		};
		
		

		
		log.info("여기야여기 : " + ageGroup);
//		log.info("여기야여기 : " + ageGroupJsonArray);


		return ageGroupJsonArray;
	}
	
	
	
	//	bar 차트(예매율 차트)에서 쓸 디비에서 가져온 데이터 JSON 데이터로 전송
	@GetMapping("/stat/bar")
	@ResponseBody
	public JSONArray bar() {
		
		List<Integer> reservationGroup = statService.reservationGroup();
		log.info("예매율 담은 값 : " + reservationGroup);
		
		//	js googleChart로 리턴할 JSON 객체
		JSONArray reservationGroupJsonArray = new JSONArray();
		
		int count = 4;
		
		for(int i = 0; count > i; i++) {
			if (i < reservationGroup.size()) {
			reservationGroupJsonArray.add(reservationGroup.get(i));
			}
		};
		
		return reservationGroupJsonArray;
	}
	
	
	
	
	//	bar2 차트(실시간 좌석 확인)에서 쓸 디비에서 가져온 데이터 JSON 데이터로 전송
	@GetMapping("/stat/bar2")
	@ResponseBody
	public JSONArray bar2() {
		
		List<Bar2Dto> seatGroup = statService.seatGroup();
		
		log.info("시트그룹 : " + seatGroup);
		
		// js 구글차트로 리턴할 제이슨 객체
		JSONArray seatGroupJsonArray = new JSONArray();
		
		int count = 3;
		
		
		for(int i = 0; count > i; i++) {
			if (i < seatGroup.size()) {
			seatGroupJsonArray.add(seatGroup.get(i));
			}
		};
		
		
		
		return seatGroupJsonArray;
	}
	
	
	
	//	donut 차트(성비별 통계)
	@GetMapping("/stat/donut")
	@ResponseBody
	public JSONArray donut() {
		
		List<Integer> genderGroup = statService.genderGroup();
		log.info("성비별 통계 담은값 : " + genderGroup);
		
		//	js 구글차트 리턴 제이슨 객체
		JSONArray genderGroupJsonArray = new JSONArray();
		
		int count = 2;
		
		for(int i = 0; count > i; i++) {
			if(i < genderGroup.size()) {
				genderGroupJsonArray.add(genderGroup.get(i));
			}
		};
		
		return genderGroupJsonArray;		
	}
	
	//	매출 추이
	@GetMapping("/stat/area")
	@ResponseBody
	public JSONArray area() {
		
		List<Integer> areaGroup = statService.areaGroup();
		log.info("매출 현황 : " + areaGroup);
		JSONArray areaGroupJsonArray = new JSONArray();
		
		int count = 3;
		
		for(int i = 0; count > i; i++) {
			if(i < areaGroup.size()) {
				areaGroupJsonArray.add(areaGroup.get(i));
			}
		};
		
	return areaGroupJsonArray;
	}

	
	
	
	
//	@GetMapping("/find")
//	public String findUserAccount(HttpServletRequest request) throws ServletException{
//
//	    log.info("get >> /user/find | findUserAccount() 실행됨.");
//	    // 세션이 만약 있고 세션 안에 loginId라는 속성도 갖고 있다면 index 페이지로 리다이렉트.
//	    HttpSession session = request.getSession(false);
//	    if(session != null && session.getAttribute("loginId") != null){
//	        return "redirect:/";
//	    }
//	    return "redirect:/user/login";
//	}
	
	
	
	@GetMapping("/boardCon")
	@ResponseBody
	public JSONObject qnaPostList() {

		List<QnaPostDto> qnaPostList = boardControlService.qnaPostList();
		
		
		JSONObject qnaPostListJsonObject = new JSONObject();
		
		
	    // qnaPostList 데이터를 JSON 객체에 추가
	    qnaPostListJsonObject.put("qnaPostList", qnaPostList);
		
		
		log.info("qna 오브젝트 : " + qnaPostListJsonObject);
		return qnaPostListJsonObject;
		
	}
	
	
	
	
	
}

