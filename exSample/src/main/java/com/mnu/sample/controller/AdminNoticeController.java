package com.mnu.sample.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.service.NoticeService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Admin/Notice")
public class AdminNoticeController {
	private final NoticeService noticeService;

	AdminNoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@RequestMapping(value = "notice_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String noticeList(@RequestParam(defaultValue = "1") int page, PageSearchDTO pgDTO, Model model) {
		int nowpage = page; // now page
		int maxlist = 10; // comment count in list
		int totpage = 1; // total page count
		int totcount = 0;// 총 글수
		if (pgDTO.getKey() != null) {
			totcount = noticeService.NoticeCountSearch(pgDTO);
		} else {
			totcount = noticeService.NoticeCount();
		}
		// count total page
		if (totcount % maxlist == 0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist + 1;
		// pagenumber check that user selected page
		int offset = (nowpage - 1) * maxlist;
		// use contents idx print
		int listcount = totcount - ((nowpage - 1) * maxlist);

		pgDTO.setOffset(offset);
		pgDTO.setMaxlist(maxlist);

		List<NoticeDTO> nList = noticeService.NoticeList(pgDTO);
		String pageSkip;
		if (pgDTO.getKey() != null) {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Admin/Notice/notice_list", maxlist,
					pgDTO.getSearch(), pgDTO.getKey());
		} else {
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Admin/Notice/notice_list", maxlist);
		}

		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("nList", nList);
		model.addAttribute("pageSkip", pageSkip);
		return "Admin/notice_list";
	}

	@GetMapping("notice_view")
	public String noticeView(@RequestParam(defaultValue = "1") int page, @RequestParam("idx") int idx, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("notice", noticeService.NoticeViewModify(idx, request, response));
		model.addAttribute("page", page);
		return "Admin/notice_view";
	}

	@GetMapping("notice_write")
	public String noticeWrite(@RequestParam(defaultValue = "1") int page) {
		return "Admin/notice_write";
	}

	@PostMapping("notice_write")
	public String noticeWritePro(@RequestParam(defaultValue = "1") int page, NoticeDTO dto) {
		noticeService.NoticeWrite(dto);
		return "redirect:/Admin/Notice/notice_list?page=" + page;
	}

	@GetMapping("notice_modify")
	public String noticeModify(@RequestParam(defaultValue = "1") int page, @RequestParam("idx") int idx, Model model) {
		model.addAttribute("notice", noticeService.NoticeModify(idx));
		model.addAttribute("page", page);
		return "Admin/notice_write";
	}

	@PostMapping("notice_modify_pro")
	public String noticeModifyPro(@RequestParam(defaultValue = "1") int page, NoticeDTO dto, Model model) {
		int row = noticeService.NoticeModifyPro(dto);
		model.addAttribute("row", row);
		model.addAttribute("idx", dto.getIdx());
		model.addAttribute("page", page);
		return "redirect:/Admin/Notice/notice_list?page=" + page;
	}

	@GetMapping("notice_delete")
	public String noticeDelete(@RequestParam(defaultValue = "1") int page, @RequestParam("idx") int idx) {
		noticeService.NoticeDelete(idx);
		return "redirect:/Admin/Notice/notice_list?page=" + page;
	}
}
