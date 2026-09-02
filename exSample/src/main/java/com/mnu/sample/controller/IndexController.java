package com.mnu.sample.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController {
	private static final Logger log = 
			LoggerFactory.getLogger(IndexController.class);
	
	@GetMapping("")
	public String getIndex(Model model) {
		log.info("call : main index");
//		model.addAttribute("nList", nList); // 최근 공지 3개
//		model.addAttribute("bList", bList); // 최근 게시글 3개
//		model.addAttribute("pList", pList); // 최근 포토 3개
		return "index";
	}
}
