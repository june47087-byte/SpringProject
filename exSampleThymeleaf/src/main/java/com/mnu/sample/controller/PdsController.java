package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Pds")
public class PdsController {
	// 자료실
	@GetMapping("pds_list")
	public String pdsList(HttpSession session) {
		return "Pds/pds_list";
	}
	// 상세페이지
	@GetMapping("pds_view")
	public String pdsView(HttpSession session) {
		return "Pds/pds_view";
	}
	// 작성
	@GetMapping("pds_write")
	public String pdsWrite(HttpSession session) {
		return "Pds/pds_write";
	}
	// 게시물 삭제
	@GetMapping("pds_delete")
	public String pdsDelete(HttpSession session) {
		return "Pds/pds_delete";
	}

}
