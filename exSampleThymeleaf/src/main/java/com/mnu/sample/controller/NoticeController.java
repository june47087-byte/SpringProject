package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Notice")
public class NoticeController {
	@GetMapping("notice_list")
	public String noticeList() {
		return "Notice/notice_login";
	}
	@GetMapping("notice_view")
	public String NoticeView() {
		return "Notice/notice_view";
	}
}
