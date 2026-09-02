package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("User")
public class UserController {
	private static final Logger log = 
			LoggerFactory.getLogger(UserController.class);
	
	//로그인 폼
	@GetMapping("user_login")
	public String userLogin() {
		log.info("User call : login");
		return "User/user_login";
	}
	//로그아웃
	@GetMapping("user_logout")
	public String userLogout() {
		log.info("User call : logout");
		return "redirect:";// index로 이동
	}
	//회원가입 폼
	@GetMapping("user_insert")
	public String userInsert() {
		log.info("User call : user_insert");
		return "User/user_insert";
	}
	//정보수정
	@GetMapping("user_modify")
	public String userModify() {
		log.info("User call : user_modity");
		return "User/user_insert";
	}
	
	
}
