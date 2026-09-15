package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Board")
public class BoardController {
	// 게시판
	@GetMapping("board_list")
	public String boardList(HttpSession session) {
		return "/Board/board_list";
	}
	// 상세페이지
	@GetMapping("board_view")
	public String boardView(HttpSession session) {
		session.invalidate();
		return "Board/board_view";
	}
	// 작성
	@GetMapping("board_write")
	public String boardWrite(HttpSession session) {
		
		return "Board/board_write";
	}
	// 작성2
	@GetMapping("board_write2")
	public String boardWrite2(HttpSession session) {
		return "Board/board_write2";
	}
	// 게시물 삭제
	@GetMapping("board_delete")
	public String boardDelete(HttpSession session) {
		return "Board/board_delete";
	}
	
}
