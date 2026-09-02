package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("Gallery")
public class GalleryController {
	
	@GetMapping("gallery_list")
	public String galleryList() {
		return "Gallery/gallery_list";
	}
	@GetMapping("gallery_view")
	public String galleryview() {
		return "Gallery/gallery_view";
	}
	@GetMapping("gallery_write")
	public String gallerywrite() {
		return "Gallery/gallery_write";
	}
}
