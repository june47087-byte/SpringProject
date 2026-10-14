package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.dto.BoardRequestDTO;
import com.mnu.sample.dto.BoardResponseDTO;
import com.mnu.sample.dto.NoticeResponseDTO;
import com.mnu.sample.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Notice")
public class NoticeController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(NoticeController.class);
	
	private final NoticeService noticeService;
	
	//검색 + 페이지 처리 + get+post
	@GetMapping("notice_list")
	public String noticeList(@ModelAttribute("page") int page, @RequestParam(value="search", required=false) String search, 
										@RequestParam(value="key", required=false) String key, 
											@PageableDefault(size=10) Pageable pageable, Model model) {
		
		log.info("Notice Call : notice_list");
		
		Page<NoticeResponseDTO> result = noticeService.noticeList(search, key, pageable);
		model.addAttribute("nList", result);
		model.addAttribute("search", search);
		model.addAttribute("key", key);
		
		return "/Notice/notice_list";
	}
	
	//리스트에서 제목 선택시 idx을 이용한 상세보기(view)
	@GetMapping("notice_view")
	public String noticeView(@RequestParam("idx") int idx, @ModelAttribute("page") int page, Model model) {
		log.info("Notice Call : notice_view");
		NoticeResponseDTO notice = noticeService.noticeView(idx);
		model.addAttribute("notice", notice);
		model.addAttribute("newLineChar", "\n");//ㄱ게시글 내용의 <br> 처리용
		//model.addAttribute("page", 1);//임시
		return "Notice/notice_view";
	}
	
}
