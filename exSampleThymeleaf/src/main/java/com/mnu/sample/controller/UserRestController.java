package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.service.EmailService;
import com.mnu.sample.service.UserService;

@RestController
@RequestMapping("User")
public class UserRestController {
	private static final Logger log = 
			LoggerFactory.getLogger(UserController.class);
	
	//UserService 주입
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailservice;
		
	//ID 중복검사
	@PostMapping("user_idCheck")
	public String userIdCheck(@RequestParam("userid") String userid) {
		log.info("User Call : user_idCheck");
		int row = userService.userIdCheck(userid);
		return String.valueOf(row);
	}
		
	//보인인증(SMS)
	@PostMapping("user_sms")
	public String smsSend(@RequestParam("tel") String tel) {
		String tempNum = userService.sendSMS(tel);
		log.info("인증번호 : " + tempNum);
		return tempNum;
	}
	//보인인증(email)
	@PostMapping("user_email")
	public String emilSend(@RequestParam("email") String email) {
		String tempNum = emailservice.sendEmail(email);
		log.info("email 인증번호 : " + tempNum);
		return tempNum;
	}
	
	// userid 이용한 사용자 검색
	@GetMapping("user_search")
	public UserDTO getUserid(@RequestParam("userid") String userid) {
		log.info("RestController :" + userid);
		return userService.getUserid(userid);
	}
	
}
