package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Admin/Gallery")
public class AdminGalleryController {
	
	//공지사함 리스트
	@GetMapping("gallery_list")
	public String admingalleryList() {
		return "Admin/gallery_list";
	}
	//공지사항 상세
	@GetMapping("gallery_view")
	public String admingalleryView() {
		return "Admin/gallery_view";
	}
	
	
}
