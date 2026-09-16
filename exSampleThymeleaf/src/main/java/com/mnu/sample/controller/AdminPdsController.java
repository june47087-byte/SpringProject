package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Admin/Pds")
public class AdminPdsController {
	
	//공지사함 리스트
	@GetMapping("pds_list")
	public String adminPdsList() {
		return "Admin/pds_list";
	}
	//공지사항 상세
	@GetMapping("pds_view")
	public String adminPdsView() {
		return "Admin/pds_view";
	}
	
	
}
