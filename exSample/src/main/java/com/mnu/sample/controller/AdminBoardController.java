package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Admin/Board")
public class AdminBoardController {
	
	//공지사함 리스트
	@GetMapping("board_list")
	public String adminBoardList() {
		return "Admin/board_list";
	}
	//공지사항 등록
	@GetMapping("board_write")
	public String adminBoardWrite() {
		return "Admin/board_write";
	}
	//공지사항 등록 폼
	@PostMapping("board_write")
	public String adminBoardWritePro() {
		return "Admin/board_list";
	}
	//공지사항 상세
	@GetMapping("board_view")
	public String adminBoardView() {
		return "Admin/board_view";
	}
	
	
}
