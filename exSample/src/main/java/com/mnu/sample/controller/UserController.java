package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.domain.UserMyPageDTO;
import com.mnu.sample.service.EmailService;
import com.mnu.sample.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("User")
public class UserController {
	private static final Logger log = 
			LoggerFactory.getLogger(UserController.class);

	//UserService 주입
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailservice;
	//로그인 폼
	@GetMapping("user_login")
	public String userLogin(HttpSession session) {
		log.info("User Call : login");
		if(session.getAttribute("user") == null) {
			return "User/user_login"; // 로그인 페이지로 이동
		}else {// 로그인한 사용자일 경우
			return "";
		}
	}
	//로그인 처리
	@PostMapping("user_login")
	public String userLoginPro(UserDTO userDTO, HttpServletRequest request) {
		log.info("user call : login_pro");
		UserDTO uDTO = userService.userLogin(userDTO);
		if(uDTO != null) {
			// 최근 로그인 날짜 업데이트
			userService.userLastTimeUpdate(uDTO.getUserid());
			// 세션 설정
			request.getSession().setAttribute("user", uDTO);
			request.getSession().setMaxInactiveInterval(600); // 10분이 보통 설정이다.
			
		}else {
			
		}
		return "User/user_login_ok"; // 경고창 
	}
	//로그아웃 처리
	@GetMapping("user_logout")
	public String userLogout(HttpSession session) {
		log.info("User Call : logout");
		session.invalidate();
		return "redirect:/";//index로 이동
	}
	
	//회원가입 폼
	@GetMapping("user_insert")
	public String userInsert() {
		log.info("User Call : userInsert");
		return "User/user_insert";
	}
	
	//ID 중복검사
	@ResponseBody
	@PostMapping("user_idCheck")
	public String userIdCheck(@RequestParam("userid") String userid) {
		log.info("User Call : user_idCheck");
		int row = userService.userIdCheck(userid);
		return String.valueOf(row);
	}
	
	
	//보인인증(SMS)
	@ResponseBody
	@PostMapping("user_sms")
	public String smsSend(@RequestParam("tel") String tel) {
		String tempNum = userService.sendSMS(tel);
		log.info("인증번호 : " + tempNum);
		return tempNum;
	}
	//보인인증(email)

	@ResponseBody
	@PostMapping("user_email")
	public String emilSend(@RequestParam("email") String email) {
		String tempNum = emailservice.sendEmail(email);
		log.info("email 인증번호 : " + tempNum);
		return tempNum;
	}
	
	//회원가입처리
	@PostMapping("user_insert")
	public String userInsertPro(UserDTO userDTO, @RequestParam("mode") String mode) {
		log.info("User Call : userInsertPro");
		userDTO.setGubun(mode);
		if(userDTO.getEmail1().equals("")) {
			userService.userInsert(userDTO);
		}else {
			if(userDTO.getEmail2().equals("")) {
				userDTO.setEmail2(userDTO.getEmail3());
			}
			userDTO.setEmail(userDTO.getEmail1()+"@"+userDTO.getEmail2());
			userService.userInsert(userDTO);
		}
		return "redirect:/User/user_login";
	}

	//정보수정 폼
	@GetMapping("user_modify")
	public String userModify(HttpSession session, Model model) {
		log.info("User Call : userModify");
		UserDTO user = (UserDTO)session.getAttribute("user");
		if(user == null) {
			return "redirect:/User/user_login"; // 미로그인시 로그인 페이지로 이동
		}
		model.addAttribute("user", user);
		return "User/user_modify";
	}

	//정보수정 처리
	@PostMapping("user_modify")
	public String userModifyPro(UserDTO userDTO, @RequestParam("mode") String mode, HttpSession session) {
		log.info("User Call : userModifyPro");
		UserDTO user = (UserDTO)session.getAttribute("user");
		if(user == null) {
			return "redirect:/User/user_login";
		}
		userDTO.setUserid(user.getUserid()); // 아이디는 세션값 그대로 사용
		userDTO.setGubun(mode);
		if(mode.equals("2")) { // 이메일 인증
			if(userDTO.getEmail2().equals("")) {
				userDTO.setEmail2(userDTO.getEmail3());
			}
			userDTO.setEmail(userDTO.getEmail1()+"@"+userDTO.getEmail2());
		}else { // 핸드폰 인증
			userDTO.setEmail(user.getEmail());
		}
		userService.userModify(userDTO);
		session.setAttribute("user", userService.userFind(user.getUserid())); // 세션 정보 갱신
		return "redirect:/User/user_modify";
	}


	//회원탈퇴(삭제)
	
	
	//ID찾기 폼
	
	
	//비번분실시 id 입력 폼
	
	
	//비번분실시 id를 찾어서 임시비번 발송

	// 마이 페이지 이동
	@GetMapping("user_mypage")
	public String userMyPage(HttpSession session, Model model) {
		log.info("User Call : userMyPage");
		UserDTO user = (UserDTO)session.getAttribute("user");
		if(user == null) {
			return "redirect:/User/user_login"; // 미로그인시 로그인 페이지로 이동
		}
		UserMyPageDTO myPage = userService.userMyPage(user.getName());
		model.addAttribute("user", user);
		model.addAttribute("myPage", myPage);
		return "User/user_mypage";
	}
	
}
