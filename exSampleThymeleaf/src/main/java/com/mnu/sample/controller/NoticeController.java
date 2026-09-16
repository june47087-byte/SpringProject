package com.mnu.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.service.NoticeService;
import com.mnu.sample.util.PageIndex;
@Controller
@RequestMapping("Notice")
public class NoticeController {
	
	//주입
	@Autowired
	private NoticeService noticeService;
	
	//공지사항 리스트(Post, Get 겸용)
	@RequestMapping(value="notice_list", method = {RequestMethod.GET, RequestMethod.POST})
	public String noticeList(@RequestParam(name = "page", defaultValue = "1") int page, PageSearchDTO pageSearchDTO, Model model) {
		
		int nowpage = page ; //넘어온 페이지 저장
		int maxlist = 10; //페이지당 글수
		int totpage = 1; //총 페이지수
		
		int totcount = 0;//총 글수
		if(pageSearchDTO.getKey() != null)
			totcount = noticeService.NoticeCountSearch(pageSearchDTO);//총 글수
		else
			totcount = noticeService.NoticeCount();
		
		// 총 페이지수 계산
		if(totcount % maxlist ==0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist + 1;
				
		int offset = (nowpage - 1) * maxlist;
		
		//게시글 일련번호 출력용
		int listcount = totcount - ((nowpage-1) * maxlist);
		
		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);
		
		List<NoticeDTO> nList = noticeService.NoticeList(pageSearchDTO);
		String pageSkip = null;
		if(pageSearchDTO.getKey() != null) {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "notice_list", maxlist, pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		}else {
			pageSkip = PageIndex.pageList(nowpage, totpage, "notice_list", maxlist);				
		}
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("nList", nList);
		model.addAttribute("pageSkip", pageSkip);
		model.addAttribute("page", page);
		model.addAttribute("search", pageSearchDTO.getSearch());
		model.addAttribute("key", pageSearchDTO.getKey());

		return "Notice/notice_list";
	}

	//공지사항 뷰(상세보기)
	@GetMapping("notice_view")
	public String noticeView(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam("idx") int idx, Model model) {
		//조회수 증가(쿠키생성)
		noticeService.noticeHits(idx);

		NoticeDTO notice = noticeService.noticeSelect(idx);
		notice.setContents(notice.getContents().replace("\n", "<br>"));
		model.addAttribute("notice", notice);
		model.addAttribute("page", page);
		return "Notice/notice_view";
	}
}
