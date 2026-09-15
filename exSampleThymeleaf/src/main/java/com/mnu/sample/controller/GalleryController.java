package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Gallery")
public class GalleryController {
	// 갤러리
	@GetMapping("gallery_list")
	public String galleryList(HttpSession session) {
		return "Gallery/gallery_list";
	}
	// 상세페이지
	@GetMapping("gallery_view")
	public String galleryView(HttpSession session) {
		return "Gallery/gallery_view";
	}
	// 작성
	@GetMapping("gallery_write")
	public String galleryWrite(HttpSession session) {
		return "Gallery/gallery_write";
	}

}
