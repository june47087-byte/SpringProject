package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.service.BoardService;
@Controller
@RequestMapping("Board")
public class BoardController {
	private static final Logger log = 
			LoggerFactory.getLogger(IndexController.class);
	private final BoardService boardService;

	BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 게시판 전체 리스트(검색 x, 페이징처리 x)
	@GetMapping("board_list")
	public String boardList(Model model) {
		log.info("board call : board_list");
		model.addAttribute("totcount", boardService.boardCount());
		model.addAttribute("bList", boardService.boardList());
		return "Board/board_list";
	}
	// 게시판 전쳋 리스트(검색 o, 페이징처리 x) mapping 이름은 같아도 가능 post, get이니까
	@PostMapping("board_list")
	public String boardList(Model model, String search, String key) {
		model.addAttribute("totcount", boardService.boardCountSearch(search, key));
		model.addAttribute("bList", boardService.boardListSearch(search, key));
		model.addAttribute("key",key);
		model.addAttribute("search",search);
		return "Board/board_list";
	}
	// 위 두가지를 합치는 방식
//	@RequestMapping(value="board_list", method= {RequestMethod.GET, RequestMethod.POST })
//	public String boardListLearch(String search, String key) { requestParam 을 사용하면 매개변수가 달라도 합칠 수 있다. 
//		return "Board/board_list";
//	}
	@GetMapping("board_view")
	public String boardview() {
		return "Board/board_view";
	}
	
	@GetMapping("board_write")
	public String boardwrite() {
		return "Board/board_write";
	}
	@PostMapping("board_write")
	public String boardwrite(BoardDTO dto) {
		boardService.boardWrite(dto);
		return "redirect:Board/board_list";
	}
	@GetMapping("board_delete")
	public String boarddlete() {
		return "Board/board_delete";
	}
}
