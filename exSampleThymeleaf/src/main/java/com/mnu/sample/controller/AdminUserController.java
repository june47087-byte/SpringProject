package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Admin/User")
public class AdminUserController {
	
	//공지사함 리스트
	@GetMapping("user_list")
	public String adminuserList() {
		return "Admin/user_list";
	}
	//공지사항 상세
	@GetMapping("user_view")
	public String adminuserView() {
		return "Admin/user_view";
	}
	
	
}
