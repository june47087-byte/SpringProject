package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			LoggerFactory.getLogger(BoardController.class);
	private final NoticeService noticeService;
	
	@GetMapping("notice_list")
	public String noticeList(@RequestParam(value="search", required=false) String search, 
								@RequestParam(value="key", required=false) String key, 
									@PageableDefault(size=10) Pageable pageable,
									Model model) {
		Page<NoticeResponseDTO> result = noticeService.noticeListSearchPage(search, key, pageable);
		model.addAttribute("nList", result);
		model.addAttribute("search", search);
		model.addAttribute("key", key);
		model.addAttribute("totcount", noticeService.noticeCount());
		return "/Notice/notice_list";
	}
	
	@GetMapping("notice_view")
	public String noticeView(@RequestParam("idx") int idx, @RequestParam("page") int page, Model model) {
		log.info("Board Call : board_view");
		NoticeResponseDTO notice = noticeService.noticeView(idx);
		model.addAttribute("notice", notice);
		model.addAttribute("newLineChar", "\n");//ㄱ게시글 내용의 <br> 처리용
		model.addAttribute("page", page);//임시
		return "/Notice/notice_view";
	}
}
