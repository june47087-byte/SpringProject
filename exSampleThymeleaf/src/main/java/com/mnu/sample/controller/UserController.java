package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/User")
public class UserController {
	// 로그인
	@GetMapping("user_login")
	public String userLogin(HttpSession session) {
		return "/User/user_login";
	}
	// 로그아웃
	@GetMapping("user_logout")
	public String userLogout(HttpSession session) {
		session.invalidate();
		return "User/user_logout";
	}
	// 회원가입폼
	@GetMapping("user_insert")
	public String userInsert(HttpSession session) {
		
		return "User/user_insert";
	}
	// 마이페이지
	@GetMapping("user_mypage")
	public String userMypage(HttpSession session) {
		return "User/user_mypage";
	}
	
}
