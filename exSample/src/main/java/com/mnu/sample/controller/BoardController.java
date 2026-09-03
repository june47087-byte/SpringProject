package com.mnu.sample.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.service.BoardService;
import com.mnu.sample.util.PageIndex;
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
//	@GetMapping("board_list")
//	public String boardList(Model model) {
//		log.info("board call : board_list");
//		model.addAttribute("totcount", boardService.boardCount());
//		model.addAttribute("bList", boardService.boardList());
//		return "Board/board_list";
//	}
	// 게시판 전체 리스트(검색 x, 페이징처리 O)
//	@GetMapping("board_list_page")
//	public String boardListPage(@RequestParam(defaultValue = "1") int page, PageSearchDTO pgDTO, Model model) {
//		log.info("board call : board_list");
//		int nowpage = page; // now page
//		int maxlist = 10; // comment count in list
//		int totpage = 1; // total page count
//		int totcount = boardService.boardCount();// 총 글수
//		// count total page
//		if(totcount % maxlist == 0) 
//			totpage = totcount / maxlist; 
//		else
//			totpage = totcount / maxlist + 1; 
//		// pagenumber check that user selected page	
//		int offset = (nowpage - 1) * maxlist;
//		// use comment idx print
//		int listcount = totcount - ((nowpage - 1) * maxlist);
//		pgDTO.setOffset(offset);
//		pgDTO.setMaxlist(maxlist);
//		String pageSkip = PageIndex.pageList(nowpage, totpage, "/Board/board_list_page", maxlist);
//		model.addAttribute("totcount", totcount);
//		model.addAttribute("totpage", totpage);
//		model.addAttribute("listcount", listcount);
//		model.addAttribute("bList", boardService.boardListPage(pgDTO));
//		model.addAttribute("pageSkip", pageSkip);
//		return "Board/board_list";
//	}
	// 게시판 전체 리스트(검색 o, 페이징처리 x) mapping 이름은 같아도 가능 post, get이니까
//	@PostMapping("board_list")
//	public String boardList(Model model, String search, String key) {
//		model.addAttribute("totcount", boardService.boardCountSearch(search, key));
//		model.addAttribute("bList", boardService.boardListSearch(search, key));
//		model.addAttribute("key",key);
//		model.addAttribute("search",search);
//		return "Board/board_list";
//	}
	// 게시판 전체 리스트(검색 o, 페이징처리 O) 
//	@PostMapping("board_list_page")
//	public String boardListSearchPage(@RequestParam(defaultValue = "1") int page, PageSearchDTO pgDTO, Model model) {
//		log.info("board call : board_list");
//		int nowpage = page; // now page
//		int maxlist = 10; // comment count in list
//		int totpage = 1; // total page count
//		int totcount = boardService.boardCountSearch(pgDTO.getSearch(), pgDTO.getKey());// 총 글수
//		// count total page
//		if(totcount % maxlist == 0) 
//			totpage = totcount / maxlist; 
//		else
//			totpage = totcount / maxlist + 1; 
//		// pagenumber check that user selected page	
//		int offset = (nowpage - 1) * maxlist;
//		// use comment idx print
//		int listcount = totcount - ((nowpage - 1) * maxlist);
//		pgDTO.setOffset(offset);
//		pgDTO.setMaxlist(maxlist);
//		String pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Board/board_list_page", maxlist, pgDTO.getSearch(), pgDTO.getKey());
//		model.addAttribute("totcount", totcount);
//		model.addAttribute("totpage", totpage);
//		model.addAttribute("listcount", listcount);
//		model.addAttribute("bList", boardService.boardListSearchPage(pgDTO));
//		model.addAttribute("pageSkip", pageSkip);
//		model.addAttribute("search", pgDTO.getSearch());
//		model.addAttribute("key", pgDTO.getKey());
//		return "Board/board_list";
//	}
	// 위 두가지를 합치는 방식
//	@RequestMapping(value="board_list", method= {RequestMethod.GET, RequestMethod.POST })
//	public String boardListLearch(String search, String key) { requestParam 을 사용하면 매개변수가 달라도 합칠 수 있다. 
//		return "Board/board_list";
//	}
	// 위의 설명의 합치는 방식을 이요해서 get post boar_list_page를 합쳐보자 게시판 전체와 검색을 합치자
	@RequestMapping(value="board_list", method= {RequestMethod.GET, RequestMethod.POST })
	public String boardListPage(@RequestParam(defaultValue = "1") int page, PageSearchDTO pgDTO, Model model) { // requestParam 을 사용하면 매개변수가 달라도 합칠 수 있다. 
		log.info("board call : board_list");
		int nowpage = page; // now page
		int maxlist = 10; // comment count in list
		int totpage = 1; // total page count
		int totcount = 0;// 총 글수
		if(pgDTO.getKey() != null) {
			totcount = boardService.boardCountSearch(pgDTO.getSearch(), pgDTO.getKey());
		}else {
			totcount = boardService.boardCount();
		}
		// count total page
		if(totcount % maxlist == 0) 
			totpage = totcount / maxlist; 
		else
			totpage = totcount / maxlist + 1; 
		// pagenumber check that user selected page	
		int offset = (nowpage - 1) * maxlist;
		// use contents idx print
		int listcount = totcount - ((nowpage - 1) * maxlist);
		
		pgDTO.setOffset(offset);
		pgDTO.setMaxlist(maxlist);
		
		List<BoardDTO> bList = null;
		String pageSkip = null;
		if(pgDTO.getKey() != null) {
			bList = boardService.boardListSearchPage(pgDTO);
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Board/board_list_page", maxlist, pgDTO.getSearch(), pgDTO.getKey());
		}else {
			bList = boardService.boardListPage(pgDTO);
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Board/board_list_page", maxlist);
		}
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("bList", bList);
		model.addAttribute("pageSkip", pageSkip);
		return "Board/board_list";
	}
	@GetMapping("board_view")
	public String boardview(@RequestParam(defaultValue = "1") int page, @RequestParam("idx") int idx, Model model) {
		model.addAttribute("board", boardService.boardViewModify(idx));
		return "Board/board_view";
	}
	
	@GetMapping("board_write")
	public String boardwrite(@RequestParam(defaultValue = "1") int page) {
		return "Board/board_write";
	}
	@PostMapping("board_write")
	public String boardwrite(@RequestParam(defaultValue = "1") int page, BoardDTO dto) {
		boardService.boardWrite(dto);
		return "redirect:/Board/board_list?page=" + page;
	}
	@GetMapping("board_delete")
	public String boarddlete() {
		return "Board/board_delete";
	}
}
